public class List {
//    private String[] taskList ;
//    private boolean[] completed ;

    private Task[] taskList ;
    private int size ;

    public List() {
        this(new String[]{}) ;
    }

    public List(String[] args) {
        this.size = args.length;

        this.taskList = new Task[100];
//        this.completed = new boolean[100];

        for (int i = 0; i < size; i++) {
            this.taskList[i] = new Task(args[i]);
        }
    }

    public Task[] getTaskList() {
        return taskList;
    }

    public void setTaskList(Task[] taskList) {
        this.taskList = taskList;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String addTask(String task) {
        taskList[size] = new Task(task);
        size++;
        return("added task: " + task);
    }

    public String markTask(int taskIndex) {
        if (taskIndex < size) {
            taskList[taskIndex].setDone(true);
            return "I've marked this as done: " + taskList[taskIndex].getTaskName();
        }
        return "ERROR. Index not in list";
    }

    public String unmarkTask(int taskIndex) {
        if (taskIndex < size) {
            taskList[taskIndex].setDone(false);
            return "I've unmarked this as done: " + taskList[taskIndex].getTaskName();
        }
        return "ERROR. Index not in list";
    }

    public String displayTasks() {

        String whole = "" ;

        for (int i = 0; i < size; i++) {
            whole += String.valueOf(i+1) ;
            whole += ":[" ;
            if (this.taskList[i].isDone()) {
                whole += "X";
            } else {
                whole += " ";
            }
            whole += "] " + this.taskList[i].getTaskName() + "\n";

//            System.out.println(whole);
        }

        return whole ;
    }
}
