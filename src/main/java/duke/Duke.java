package duke;

import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.Todo;

import java.util.Scanner;

public class Duke {
    /**
     * Command line output constants
     * */
    final static String DIVLINE = "\t__________________________________________________________________\n";
    final static String GREETINGS = "\tHello! I'm Ayanga, your personal task manager.\n" +
            "\tWhat can I note down for you?\n" +
            "\tTo add a todo item, simply write \"todo <DESCRIPTION>\".\n" +
            "\tTo add a deadline, write \"deadline <DESCRIPTION> /by <TIME>\".\n" +
            "\tTo add an event, write \"event <DESCRIPTION> /at <VENUE>\".\n" +
            "\tSay \"list\" and I will display your tasks.\n" +
            "\tWrite \"done <NUMBER OF TASK>\" to let me know when you have completed a task.\n" +
            "\tWave \"bye\" to me if you don't need me for now.\n";
    final static String PARTINGS = "\tBye. Hope you have done your work next time I see you!\n" +
            "\tAh, and also remember to take care of yourself and sleep early :)\n";
    private static boolean isExiting = false;       // control the loop in main
    private static Task[] tasks = new Task[100];    // list of task items
    private static int taskCount = 0;               // keep track of total task numbers

    /**
     * Deals with raw input, extracts keyword and calls respective methods.
     * Catches the exceptions of each method and print corresponding response.
     * Throws an exception if keyword is invalid.
     * @param   prompt  is the raw input string
     * */
    public static void processPrompt(String prompt) throws DukeException {
        String keyword = prompt.contains(" ") ? prompt.split(" ")[0] : prompt;
        switch (keyword) {
        case "bye":
            isExiting = true;
            break;
        case "list":
            try {
                displayList();
            } catch (DukeException e) {
                System.out.println(DIVLINE + e.getEmptyListMessage());
                System.out.print(DIVLINE);
            }
            break;
        case "done":
            try {
                int taskIndex = Integer.parseInt(prompt.substring(5)) - 1;
                completeTask(taskIndex);
            } catch (NumberFormatException e) {
                System.out.println(DIVLINE + "\t:( OOPS!!! You are not specifying a valid task number.");
                System.out.print(DIVLINE);
            } catch (DukeException e) {
                System.out.println(DIVLINE + e.getTaskAlreadyDoneMessage());
                System.out.print(DIVLINE);
            }
            break;
        case "deadline":
            try {
                addDeadlineToList(prompt.substring(8));
            } catch (DukeException e) {
                System.out.println(DIVLINE + e.getIllegalDeadlineMessage());
                System.out.print(DIVLINE);
            }
            break;
        case "event":
            try {
                addEventToList(prompt.substring(5));
            } catch (DukeException e) {
                System.out.println(DIVLINE + e.getIllegalEventMessage());
                System.out.print(DIVLINE);
            }
            break;
        case "todo":
            try {
                addTodoToList(prompt.substring(4));
            } catch (DukeException e) {
                System.out.println(DIVLINE + e.getIllegalTodoMessage());
                System.out.print(DIVLINE);
            }
            break;
        case "delete":
            try {
                int taskIndex = Integer.parseInt(prompt.substring(7)) - 1;
                deleteFromList(taskIndex);
            } catch (NumberFormatException e) {
                System.out.println(DIVLINE + "\t:( OOPS!!! You are not specifying a valid task number.");
                System.out.print(DIVLINE);
            } catch (DukeException e) {
                System.out.println(DIVLINE + "Target task does not exist or you have specified nothing.");
                System.out.print(DIVLINE);
            }
        default:
            throw new DukeException("Illegal keyword.");
        }
         return;
    }

    /**
     * */
    private static void displayList() throws DukeException {
        if (taskCount == 0) {
            throw new DukeException("Empty list. Nothing to be displayed.");
        }
        System.out.print(DIVLINE);
        System.out.println("\tHere are the tasks in your list:");
        for (int i = 0; i < taskCount; i++) {
            System.out.println("\t" + (i + 1) + "." + tasks[i].toString());
        }
        System.out.print(DIVLINE);
    }

    private static void addDeadlineToList(String description) throws DukeException {
        if (description.startsWith(" ")){
            String ddlDscp = description.substring(1);
            int splitPoint = ddlDscp.indexOf("/by");
            if (splitPoint==-1){
                throw new DukeException("Illegal Deadline prompt detected.");
            }
            tasks[taskCount] = new Deadline(ddlDscp.substring(0, splitPoint - 1),
                    ddlDscp.substring(splitPoint + 4));
            printAddSuccessMessage(tasks[taskCount]);
            taskCount++;
        } else {
            throw new DukeException("Illegal Deadline prompt detected.");
        }
    }

    private static void addEventToList(String description) throws DukeException {
        if (description.startsWith(" ")){
            String evtDscp = description.substring(1);
            int splitPoint = evtDscp.indexOf("/at");
            if (splitPoint==-1){
                throw new DukeException("Illegal Event prompt detected.");
            }
            tasks[taskCount] = new Event(evtDscp.substring(0, splitPoint - 1),
                    evtDscp.substring(splitPoint + 4));
            printAddSuccessMessage(tasks[taskCount]);
            taskCount++;
        } else {
            throw new DukeException("Illegal Event prompt detected.");
        }
    }

    private static void addTodoToList(String description) throws DukeException {
        if (description.startsWith(" ")) {
            tasks[taskCount] = new Todo(description.substring(1));
            printAddSuccessMessage(tasks[taskCount]);
            taskCount++;
        } else {
            throw new DukeException("Illegal Todo Prompt detected.");
        }
    }

    private static void printAddSuccessMessage(Task task) {
        System.out.print(DIVLINE);
        System.out.println("\tGot it. I've added this task:\n" +
                "\t  " + task.toString());
        System.out.println("\tNow you have " + (taskCount+1) + " tasks in the list.");
        System.out.print(DIVLINE);
    }

    private static void printDeleteSuccessMessage(Task task) {
        System.out.print(DIVLINE);
        System.out.println("\tNoted. I've removed this task: \n" +
                "\t  " + task.toString());
        System.out.println("\tNow you have " + (taskCount-1) + " tasks in the list.");
        System.out.print(DIVLINE);
    }

    private static void completeTask(int taskIndex) throws DukeException {
        if (tasks[taskIndex].getIsDone()){
            throw new DukeException("Task has already been marked done.");
        }
        tasks[taskIndex].markAsDone();
        System.out.print(DIVLINE);
        System.out.println("\tNice! I've marked this task as done: \n" +
                "\t" + tasks[taskIndex].toString());
        System.out.print(DIVLINE);
    }

    private static void deleteFromList(int taskIndex) throws DukeException {
        if (taskIndex > taskCount) {
            throw new DukeException("Task does not exist");
        }
        Task toBeDeleted = tasks[taskIndex];
        // Some manipulation here
        taskCount--;
        printDeleteSuccessMessage(toBeDeleted);
    }

    public static void main(String[] args) {
        System.out.print(DIVLINE + GREETINGS + DIVLINE);
        Scanner in = new Scanner(System.in);
        while (!isExiting) {
            try{
                String prompt = in.nextLine();
                processPrompt(prompt);
            } catch (DukeException e) {
                System.out.println(DIVLINE + e.getIllegalKeywordMessage());
                System.out.print(DIVLINE);
            }
        }
        System.out.println(DIVLINE + PARTINGS + DIVLINE);
    }


}