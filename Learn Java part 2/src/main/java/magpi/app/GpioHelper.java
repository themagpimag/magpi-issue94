package magpi.app;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

/**
 * Helper class which bundles the Pi4J functions.
 */
public class GpioHelper {

    /**
     * The pins we are using in our example.
     */
    private static final Pin PIN_LED = RaspiPin.GPIO_03;        // BCM 22
    private static final Pin PIN_BUTTON = RaspiPin.GPIO_05;     // BCM 24

    /**
     * The connected hardware components.
     */
    private GpioController gpioController;

    /**
     * The Pi4J GPIO input and outputs.
     */
    private GpioPinDigitalOutput led = null;
    private GpioPinDigitalInput button = null;

    /**
     * The GPIO handlers.
     */
    private ButtonListener buttonListener = null;

    /**
     * Constructor.
     */
    public GpioHelper() {
        try {
            // Initialize the GPIO controller
            this.gpioController = GpioFactory.getInstance();

            // Initialize the led pin as a digital output pin with initial low state
            this.led = gpioController.provisionDigitalOutputPin(PIN_LED, "RED", PinState.LOW);
            this.led.setShutdownOptions(true, PinState.LOW);

            // Initialize the input pin with pull down resistor
            this.buttonListener = new ButtonListener();
            this.button = gpioController.provisionDigitalInputPin(PIN_BUTTON, "Button",
                    PinPullResistance.PULL_DOWN);
            button.addListener(this.buttonListener);
        } catch (UnsatisfiedLinkError | IllegalArgumentException ex) {
            System.err.println("Problem with Pi4J!"
                    + " Probably running on non-Pi-device or Pi4J not installed. Error: "
                    + ex.getMessage());
        }
    }

    public void toggleLed() {
        if (this.led != null) {
            if (this.led.isLow()) {
                this.led.high();
            } else {
                this.led.low();
            }
        }
    }

    public ButtonListener getButtonListener() {
        return this.buttonListener;
    }

    public void close() {
        this.gpioController.shutdown();
    }
}