package org.example.thread.producer_consumer.blocking_queue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;

/**
 *
 */
public class PriorityBlockingQueueTest {
    private static class Employee implements Comparable<Employee>{
        private Integer id;
        private String name;
        private LocalDate dob;
        public Employee(int id, String name, LocalDate dob) {
            this.id = id;
            this.name = name;
            this.dob = dob;
        }
        public Integer getId() {
            return id;
        }
        public String getName() {
            return name;
        }
        public LocalDate getDob() {
            return dob;
        }
        @Override
        public int compareTo(Employee o) {
            return this.id.compareTo(o.id);
        }

        @Override
        public String toString() {
            return "Employee{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", dob=" + dob +
                    '}';
        }
    }
    public static void main(String[] args) {
        PriorityBlockingQueue<Employee> queue = new PriorityBlockingQueue<>(6);
        queue.add(new Employee(100, "ABC", LocalDate.of(1988,12,01)));
        queue.add(new Employee(101, "ABD", LocalDate.of(1984,6,12)));
        queue.add(new Employee(102, "XYZ", LocalDate.of(1988,12,11)));
        queue.add(new Employee(104, "XXX", LocalDate.of(1988,9,21)));
        queue.add(new Employee(110, "SSS", LocalDate.of(1988,5,3)));
        queue.add(new Employee(10, "SVC", LocalDate.of(1988,01,8)));
        //Natural Ordering - used Employee.compareTo() method to set priority of elements
        while (true){
            Employee employee = queue.poll();
            if(employee == null){
                break;
            }
            System.out.println("Employee - "+employee);
        }
        //Externally provided ordering
        Comparator<Employee> comparator = Comparator.comparing(Employee::getDob);
        PriorityBlockingQueue<Employee> queue1 = new PriorityBlockingQueue<>(6, comparator);
        queue1.add(new Employee(100, "ABC", LocalDate.of(1988,12,01)));
        queue1.add(new Employee(101, "ABD", LocalDate.of(1984,6,12)));
        queue1.add(new Employee(102, "XYZ", LocalDate.of(1988,12,11)));
        queue1.add(new Employee(104, "XXX", LocalDate.of(1988,9,21)));
        queue1.add(new Employee(110, "SSS", LocalDate.of(1988,5,3)));
        queue1.add(new Employee(10, "SVC", LocalDate.of(1988,01,8)));
        while (true){
            Employee employee = queue1.poll();
            if(employee == null){
                break;
            }
            System.out.println("Employee - "+employee);
        }
        PriorityBlockingQueue<Integer> queue2 = new PriorityBlockingQueue<>(6);
        queue2.add(3);
        queue2.add(5);
        queue2.add(1);
        queue2.add(5);
        queue2.add(33);
        queue2.add(31);
        List<Integer> list = new ArrayList<>();
        //drain/ remove 3 elements
        queue2.drainTo(list,3);
        System.out.println("Elements - "+list);
        list.removeAll(list);
        //drain/ remove all elements
        queue2.drainTo(list);
        System.out.println("Elements - "+ list);
    }
}
