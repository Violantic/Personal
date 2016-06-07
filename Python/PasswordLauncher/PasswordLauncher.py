#Imports the System OS import and the Time import.

import os
import time

#Initial run method
def run():

    #Displays a help menu for users when the application loads.
    
    print("Help: ")
    print("When entering an application name do not include the exe or the dot. Only say 'cmd'")
    print("When entering an extention name do not include the beginning or the dot. Only say 'exe'")
    print("")

    #Enter the application name E.X: cmd
    
    application = input("Enter an application: ")

    #Enter the application extension without the dot E.X: exe
    
    extention = input("Enter the applications file exention without the dot. Example: txt: ")
    
    print("Loading..")

    with open("Computing-documents/passwords.txt") as files: #Loads the password file
        fileLines = files.readline()

    password = input("Enter the password to launch the appliaction: ") #Enter your password

    if password.lower() == fileLines.lower(): #Checks the password file to see if the password exists.
        print("Correct password!")

        times = open("Computing-documents/times.txt", 'r') #Loads time file.

        print("The last login on this account was on " + str(times.readlines(0)) + ". Your current username/login is " + os.getlogin() + "!")
        #Prints when you last entered a password and got it correct much like Linux and prints your username
        
        times = open("Computing-documents/times.txt", 'w') #Loads times file
        times.write(time.strftime("%H:%M:%S, %d/%m/%y")) #Saves the last time you logged in in the format: HOUR:MINUTE:SECOND, DAY/MONTH/YEAR
        times.close() #Closes times file.

        #Runs my loadApplication function/method.
        loadApplication(application + "." + extention)
        
    else: #Only prints if password is wrong.
        
        print("Wrong password! Please try again.")
        run() #Restarts

#Function/method to load an application.
def loadApplication(app):

    try:
        #Uses the OS import to start a file on your computer.
        os.startfile(app)
        print("File loaded..")
    except Exception:
        #Only throws if the file does not exist.
        print("The application " + app + " does not exist!")
        
        tryAgain = input("Do you want to try again [Yes or Y]: ") #Choose if you want to try and load an application again.

        if tryAgain.lower() == "Yes".lower() or tryAgain.lower() == "Y".lower():
            run()
        else:
            print("Close now..")
            

#Initial Run
run()
