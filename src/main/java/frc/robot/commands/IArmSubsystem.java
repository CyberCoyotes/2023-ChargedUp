package frc.robot.commands;

public interface IArmSubsystem {
    void SetToPosition(int setPoint);
    int GetPosition();
}
