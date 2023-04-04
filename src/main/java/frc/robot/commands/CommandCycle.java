package frc.robot.commands;

import java.util.ArrayList;

import edu.wpi.first.wpilibj2.command.Command;

public class CommandCycle 
{
    private Command[] commands;
    private Command selectedCommand;
    private int iterator;
    public CommandCycle(Command... commands)
    {
        commands = new Command[commands.length];

    }
        //Dont use this
    public Command GetCommand()
    {
        return commands[iterator % commands.length];
    }
    public Command Get()
    {
        return selectedCommand;
    }
    public void Increment()
    {
        iterator++;
        selectedCommand = commands[iterator % commands.length];
    } 
        
}
