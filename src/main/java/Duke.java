import java.util.Scanner;

public class Duke {
    /**
     * Command line output constants
     * */
    final static String DIVLINE = "\t__________________________________________________________________\n";
    final static String GREETINGS = "\tHello! I'm Ayanga, your personal task manager.\n" +
            "\tWhat can I note down for you?\n" +
            "\tWave \"bye\" to me if you don't need me for now;\n" +
            "\tSay \"list\" and I will display your tasks.\n" +
            "\tSay \"done\" and a number to let me know you completed which task.\n";
    final static String PARTINGS = "\tBye. Hope you have done your work next time I see you!\n" +
            "\tAh, and also remember to take care of yourself and sleep early :)\n";
    private static boolean isExiting = false;

    /**
     * Deals with raw input, extracts keyword and calls respective methods.
     * @param   prompt  is the raw input string
     * */
    public static void processPrompt(String prompt) {
        String keyword = prompt.contains(" ") ? prompt.split(" ")[0] : prompt;
        System.out.print(DIVLINE);
        switch(keyword){
        case "bye":
            break;
        case "list":
            break;
        case "done":
            break;
        case "deadline":
            break;
        case "event":
            break;
        default:
            break;
        }

//            if (prompt.equals("list")) {
//                displayList(tasks, taskIndex);
//            } else if (prompt.startsWith("done")) {
//                completeTask(tasks, prompt.substring(5));
//            } else if (prompt.startsWith("todo")) {
//                addToList(tasks, prompt.substring(5), taskIndex, "todo");
//                taskIndex++;
//            } else if (prompt.startsWith("deadline")) {
//                addToList(tasks, prompt.substring(9), taskIndex, "deadline");
//                taskIndex++;
//            } else if (prompt.startsWith("event")) {
//                addToList(tasks, prompt.substring(6), taskIndex, "event");
//                taskIndex++;
//            } else {
//                System.out.println("Invalid prompt. Please try other keywords.");
//            }
        System.out.print(DIVLINE);
        return;
    }

    public static void displayList(Task[] tasks, int taskIndex) {
        if (taskIndex == 0) {
            System.out.println("\tYou haven't noted down anything yet.");
            return;
        }
        System.out.println("\tHere are the tasks in your list:");
        for (int i = 0; i < taskIndex; i++) {
            System.out.println("\t" + (i + 1) + "." + tasks[i].toString());
        }
    }

    private static void addToList(Task[] tasks, String prompt, int taskIndex, String taskType) {
        int cutOffPoint = 0;
        switch (taskType) {
        case "todo":
            tasks[taskIndex] = new Todo(prompt);
            break;
        case "deadline":
            cutOffPoint = prompt.indexOf("/by");
            tasks[taskIndex] = new Deadline(prompt.substring(0, cutOffPoint - 1), prompt.substring(cutOffPoint + 4));
            break;
        case "event":
            cutOffPoint = prompt.indexOf("/at");
            tasks[taskIndex] = new Event(prompt.substring(0, cutOffPoint - 1), prompt.substring(cutOffPoint + 4));
            break;
        }
        System.out.println("\tGot it. I've added this task:\n" +
                "\t  " + tasks[taskIndex].toString());
        System.out.println("\tNow you have " + (taskIndex + 1) + " tasks in the list.");
    }

    private static void completeTask(Task[] tasks, String substring) {
        int taskIndex = Integer.parseInt(substring) - 1;
        tasks[taskIndex].markAsDone();
        System.out.println("\tNice! I've marked this task as done: \n" +
                "\t" + tasks[taskIndex].toString());
        //bug here: does not deal with overflow values of input
    }

    public static void main(String[] args) {
        Task[] tasks = new Task[100];   // list of task items
        System.out.print(DIVLINE + GREETINGS + DIVLINE);
        Scanner in = new Scanner(System.in);
        int taskIndex = 0;
        while (!isExiting) {
            String prompt = in.nextLine();
        }


        System.out.println(DIVLINE + PARTINGS + DIVLINE);
    }


}
