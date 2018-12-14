package Restaurant.Food.FoodMachine;

import Restaurant.Util.GlobalClock;
import Restaurant.Util.Timestamp;

// Abstract class to represent food machines
public abstract class FoodMachine {

    public enum MACHINE_STATUS
    {
        IDLE, BUSY
    }

    public static GlobalClock globalClock;

    private String machineType;
    private MACHINE_STATUS machineStatus;
    private Timestamp workDuraion;

    FoodMachine(Timestamp workDuration)
    {
        this.workDuraion = workDuration;
        this.SetMachineStatus(MACHINE_STATUS.IDLE);
    }

    public void StartWorking()
    {
        GlobalClock.Sleep(workDuraion, globalClock);
    }

    public void FinishWorking()
    {
        SetMachineStatus(MACHINE_STATUS.IDLE);
    }

    public String GetMachineType()
    {
        return this.machineType;
    }

    void SetMachineType(String machineType)
    {
        this.machineType = machineType;
    }

    public synchronized MACHINE_STATUS GetMachineStatus()
    {
        MACHINE_STATUS curMachineStatus = this.machineStatus;
        this.machineStatus = MACHINE_STATUS.BUSY;
        return curMachineStatus;
    }

    private synchronized void SetMachineStatus(MACHINE_STATUS machineStatus)
    {
        this.machineStatus = machineStatus;
    }
}
