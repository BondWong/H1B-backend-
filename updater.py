#!/usr/bin/env python
import sys
from openpyxl import load_workbook
import threading

task_num_per_consumer = 100
row_num_in_task = 60

def main():
    filePath = sys.argv[1]
    tasks = {}
    condition = threading.Condition()
    wb = load_workbook(filePath, read_only=True)
    sheet = wb.worksheets[0]
    row_count = sheet.max_row

    producer = TaskCreater(tasks, filePath, condition, row_count, row_num_in_task)
    producer.start()
    producer.join()

    consumers = []
    while True:
        task_num = task_num_per_consumer if task_num_per_consumer * row_num_in_task <= row_count else 1 
        consumer = TaskHandler(tasks, condition, task_num)
        consumer.start()
        consumer.join()
        consumers.append(consumer)
        row_count -= task_num * row_num_in_task

        if row_count <= 0:
            break

class TaskCreater(threading.Thread):
    def __init__(self, tasks, filePath, condition, task_num, row_num_in_task):
        threading.Thread.__init__(self)
        self.tasks = tasks
        self.condition = condition
        self.filePath = filePath
        self.task_num = task_num
        self.row_num_in_task = row_num_in_task

    def run(self):
        for i in range(2, self.task_num, self.row_num_in_task):
            start = i
            end = i + self.row_num_in_task if i + self.row_num_in_task <= self.task_num else self.task_num 
            self.condition.acquire()
            self.tasks[len(self.tasks)] = {"id": len(self.tasks), "start": start, "end": end, "isHandled": False}
            self.condition.notify()
            self.condition.release()
        
class TaskHandler(threading.Thread):
    def __init__(self, tasks, condition, task_num):
        threading.Thread.__init__(self)
        self.tasks = tasks
        self.condition = condition
        self.task_num = task_num
    
    def run(self):
        for i in range(self.task_num):
            self.condition.acquire()
            if len(self.tasks) == 0:
                self.condition.wait()
            
            task = None
            for t in self.tasks.values():
                if t["isHandled"] == False:
                    task = t
                    t["isHandled"] = True
                    break
            self.condition.release()

            # create
            print task

            if task:
                self.condition.acquire()
                del self.tasks[task["id"]]
                self.condition.release()

            
main()
