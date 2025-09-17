# Shiroha User Guide 
## Introduction
### What is Shiroha? :cloud:
Shiroha is a chatbot that allows you to add, track, and delete different tasks through a list of commands
Shiroha allows 4 different types of tasks to be added, which include a simple todo task, a task with a deadline, an event, and a recurring task
### Who is this for?
This chatbot is for you! Who needs something to easily track all the tasks that you would have forgotten.
### How this guide is organized :question:
Here is the index:
[Quick Start](#quick-start) :smile:
[Concepts](#concepts) :heart:
[Command List](#commands) :+1:
[Errors?](#error-handling) :bug:
[FAQ](#faq) :question:


## Quick Start

### Installation (Windows)
Click this [link](https://github.com/Anxinal/ip/releases/latest) and download
### First run
Run the application directly 
### Basic workflow 
Refer to the text box. Put in the command and press send every time
You can try to start adding a simple `todo` command and use `list` to show it.
Make sure that your command is supported or appears on this user guide!

## Concepts

1. **Task types**: For each task we track in the chatbot, it comes with different types , which includes "todo", "event", "deadline", "recurring". 
Every task can be marked done with the mark command and unmarked using the unmark command. When a task is done and marked, the box in front of the task description will look like `[X]`


2. **Todo** : A simple task that only has a description.

3. **Deadline** : A  task that has a description and a deadline. The program will show (Overdue!) on the task description when the due is past.

4. **Event** : A task that has a description and a starting/ending time. The program will show (Happening!) on the task description when today is within the event period.

5. **Recurring** : A task that has a description and a starting time and an interval(in number of days). Each happening of the event is calculated by the formula `starting date + interval(in days) * n`. The program will show the most recent happening of the event **past and including** today

## Configuration

### Global config file & location
The configuration file, including all memory data of the chatbot, is saved in a special file named `data/shirohaTaskMemory.mem`.

### Default date/time format preferences
The default date format is `DD-MM-YYYY`.(E.g. 05-07-2025 means 5 Jul 2025)

### Autosave & backup settings
The task list memory is auto saved when the program exits normally.

## Commands 

All `<Date>` follows the [defualt date format](#default-datetime-format-preferences)
### Creating tasks
`todo <taskname>`: creates a new todo task
  `<taskname>`: String representation of the task

`deadline <taskname> /by <Date>` creates a new deadline task
  `<taskname>` :String representation of the task
  `<Date1>` :The date where the task starts in the default date format

 `event <taskname> /from <Date1> /to <Date2>` creates a new event
  `<taskname>`: String representation of the task
  `<Date1>` :The date where the task starts in the default date format
  `<Date2>`: The date where the task ends in the default date format

`recur <taskname> /start <Date> /rate <i>` creates a new recurring task that starts from the date and repeats every i days
  `<taskname>` :String representation of the task
  `<Date>`: The date where the task starts in the default date format
  `<i>` :An integer representing the number of days between each occurence of the event

### Editing/Deleting Task

`delete <id>` Delete the task by numeric id shown on the list
  `<id>`: An integer representing the index of the task on the list 

`mark <id>` Mark a task as done on the list 
`<id>`: An integer representing the index of the task on the list 

`unmark <id>` Unmark a task as done on the list 
`<id>` :An integer representing the index of the task on the list 

### Search Tasks
`find <key>` Find all tasks that contains the key in the task list
`<key>`: A string where all tasks whose description matches or contains it will be returned

## Error handling

### Common errors and meanings
An error is returned when the dialog box of the chat bot turns red 
The error message is usually indicative of where the problem is with your command
The following are a few examples 

### Invalid date/time
It usually returns the following error message  
>"Your date seems weird..."

Remember to check the default date format before inputing the command

### Unknown command / option
It usually returns the following error message  
>"I don't understand what you are saying"

Remember to check all available commands



## FAQ

### How are IDs assigned?
The IDs are assigned in the ascending numerical order.
Whenever a task is deleted. The order will be readjusted such that the **index will always increase only by 1 between adjacent tasks in the list**
### What date formats are supported?
See [defualt date format](#default-datetime-format-preferences).
Sadly, other date formats are currently unsupported


