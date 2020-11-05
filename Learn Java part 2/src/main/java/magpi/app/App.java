package magpi.app;

public class App {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Started");

        GpioHelper gpioHelper = new GpioHelper();

        while (gpioHelper.getButtonListener().getPressed() < 10) {
            gpioHelper.toggleLed();
            Thread.sleep(250);
        }

        gpioHelper.close();

        System.out.println("Finished");
    }
}
