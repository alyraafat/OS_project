# Operating System Simulation

In this project, we implemented a basic interpreter. We have a text file that represents a program. We are provided with 3 program files each representing a program. We created an interpreter that reads the txt files and executes their code. We implemented a memory and save the processes in it. We implemented mutexes that ensure mutual exclusion over the critical resources. And finally, we implemented a scheduler that schedules the processes that we have in our system.

### System Calls

System calls are the process's way of requesting a service from the OS. In order for a process to be able to use any of the available hardware, it makes a request, system call, to the operating system.

Types of system calls implemented:
1. Read the data of any file from the disk.
2. Write text output to a file in the disk.
3. Print data on the screen.
4. Take text input from the user.
5. Reading data from memory.
6. Writing data to memory.

### Memory

One of the steps the OS does in order to create a new process is allocating a space for it in the main memory. The OS is responsible for managing the memory and its allocation.

The memory is of a fixed size. It is made up of 40 memory words. The memory is large enough to hold the un-parsed lines of code, variables and PCB for any of the processes. The memory is divided into memory words, each word can store 1 variable and its corresponding data. Processes should not access any data outside their allocated memory block.


## Scheduler

A scheduler is responsible for scheduling between the processes in the Ready Queue. It ensures that all processes get a chance to execute. A scheduling Algorithm is an algorithm that chooses the process that gets to execute.

In this project, we implemented the Round Robin algorithm. Round robin is a scheduling algorithm where each process is assigned a fixed time slice.

### Queues

- Ready Queue: For the processes currently waiting to be chosen to execute on the processor
- Blocked Queue: For the processes currently waiting for resources to be available

## Output

The Simulated OS outputs the following during execution:
- Queues should be printed after every scheduling event, i.e., when a process is chosen, blocked, or finished.
- Which process is currently executing.
- The instruction that is currently executing.
- Time slice is subject to change, i.e., you might be asked to change it to x instructions per time slice.
- Order in which the processes are scheduled are subject to change.
- The timings in which processes arrive are subject to change.
- The memory shown every clock cycle in a human-readable format.
- The ID of any process whenever it is swapped in or out of disk.
- The format of the memory stored on Disk.

## Authors

- [Aly Raafat](https://github.com/alyraafat)
- [Omar Ashraf](https://github.com/OmarAshraf-02)
- [Karim Gamaleldin](https://github.com/karimgamaleldin)
- [Basel Farouk](https://github.com/basel68)
- [Marwan Amgad](https://github.com/marwanamgad)
