package magpi.app;

import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

/**
 * Listener which will be called each time the button value changes.
 */
public class ButtonListener implements GpioPinListenerDigital {

    private long pressed = 0;

    /**
     * Event handler for the button
     */
    @Override
    public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
        if (event.getState().equals(PinState.LOW)) {
            this.pressed++;
            System.out.println("Button is pressed for the " + 
                this.pressed + "th time");
        }
    }

    public long getPressed() {
        return this.pressed;
    }
}