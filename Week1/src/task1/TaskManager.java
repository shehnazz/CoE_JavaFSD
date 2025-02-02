package task1;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class TaskManager {
	private PriorityQueue<Task> taskQueue;
    private Map<String, Task> taskMap;

    public TaskManager() {
        taskQueue = new PriorityQueue<>(Comparator.comparingInt(Task::getPriority).reversed());
        taskMap = new HashMap<>();
    }

    public void addTask(String id, String description, int priority) {
        if (taskMap.containsKey(id)) {
            System.out.println("Task with ID " + id + " already exists.");
            return;
        }
        Task task = new Task(id, description, priority);
        taskQueue.offer(task);
        taskMap.put(id, task);
    }

    public void removeTask(String id) {
        Task task = taskMap.remove(id);
        if (task != null) {
            taskQueue.remove(task);
        } else {
            System.out.println("Task with ID " + id + " not found.");
        }
    }

    public Task getHighestPriorityTask() {
        return taskQueue.peek();
    }
    
    public static void main(String[] args) {
        TaskManager manager = new TaskManager();
        manager.addTask("1", "Complete assignment", 2);
        manager.addTask("2", "Prepare for meeting", 1);
        manager.addTask("3", "Submit report", 3);
        
        System.out.println("Highest Priority Task: " + manager.getHighestPriorityTask());
        
        manager.removeTask("3");
        System.out.println("Highest Priority Task after removal: " + manager.getHighestPriorityTask());
    }

}
