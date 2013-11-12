CecilRPi
=========================================
Description:
Cecil based Raspberry Pi framework
=========================================
Cecil is designed to be the assembly language for a virtual processor called SIM. 
We are developing a grammar for it including a lexer, parser and compiler.
Its virtual machine has its own memory and I/O ports. 
The virtual processor SIM has three registers, three status flags and an instruction set. 
This instruction set comprises simple instructions like load, add, sub, insert. 
These can be used to create more complicated sub-routines like loops and if-else statements. 
This is particularly useful to understand how the machine breaks down a seemingly atomic for-loop statement into multiple operations. 
To add a fun element, we are also going to add an interactive LED display by using the physical GPIO ports on the PI. 
They will depict the value at the current memory location.

Basic GitHub Shell commands for Developers: 

0. git clone git@github.com:GDP9/CecilRPi.git : SSH connection only for the first time to clone the repository to 
                                                the local machine

1. git add --all : to add the files for commit
2. git commit -m 'commit message' : commits to the local repository
3. git push origin master : syncs the github GDP/CecilRPi repository by pushing the local committed changes

