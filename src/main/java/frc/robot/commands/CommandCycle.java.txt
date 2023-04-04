package frc.robot.commands;

import java.util.ArrayList;

import edu.wpi.first.wpilibj2.command.Command;

public class CommandCycle 
{
    private Command[] commands;
    private int iterator;
    public CommandCycle(Command... commands)
    {
        commands = new Command[commands.length];

    }
    
    public Command Get()
    {
        return commands[iterator % commands.length];
    }
    public void Increment()
    {
        iterator++;
    } 
        
}
