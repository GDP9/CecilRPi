/**
 * 
 */
package org.raspberrypi.cecil.model.interfaces;

import org.raspberrypi.cecil.model.outputstream.ErrorOutputStream;
import org.raspberrypi.cecil.model.outputstream.StandardOutputStream;

/**
 * @author sa10g10
 *
 */
public interface ConsoleInterface {

	public ErrorOutputStream getErrorOutputStream();
	public StandardOutputStream getStandardOutputStream();
}
