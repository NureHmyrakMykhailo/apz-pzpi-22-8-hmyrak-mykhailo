// 1. Інтерфейс команди
public interface ICommand
{
    void Execute();
    void Undo();
}

// 2. Приймач
public class Light
{
    public void On() => Console.WriteLine("Світло увімкнено.");
    public void Off() => Console.WriteLine("Світло вимкнено.");
}

// 3. Конкретні команди
public class LightOnCommand : ICommand
{
    private Light _light;

    public LightOnCommand(Light light)
    {
        _light = light;
    }

    public void Execute() => _light.On();
    public void Undo() => _light.Off();
}

public class LightOffCommand : ICommand
{
    private Light _light;

    public LightOffCommand(Light light)
    {
        _light = light;
    }

    public void Execute() => _light.Off();
    public void Undo() => _light.On();
}

// 4. Ініціатор
public class RemoteControl
{
    private ICommand _command;

    public void SetCommand(ICommand command)
    {
        _command = command;
    }

    public void PressButton()
    {
        _command.Execute();
    }

    public void PressUndo()
    {
        _command.Undo();
    }
}

// 5. Клієнтський код
class Program
{
    static void Main()
    {
        Light livingRoomLight = new Light();
        ICommand lightOn = new LightOnCommand(livingRoomLight);
        ICommand lightOff = new LightOffCommand(livingRoomLight);

        RemoteControl remote = new RemoteControl();

        // Вмикаємо світло
        remote.SetCommand(lightOn);
        remote.PressButton();

        // Вимикаємо світло
        remote.SetCommand(lightOff);
        remote.PressButton();

        // Undo
        remote.PressUndo();
    }
}
