package frc.robot.commands;

import java.util.ArrayList;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;

public class CommandCycle 
{
    private Command[] commands;
    private int iterator;
    public CommandCycle(Command... commands)
    {
        this.commands = new Command[commands.length];

    }
    
    public Command Get()
    {
        var output = commands[iterator % commands.length];
        if (output == null) {
        return new InstantCommand();
            
        }
        return output;
    }
    public void Increment()
    {
        iterator++;
    } 
        
}
