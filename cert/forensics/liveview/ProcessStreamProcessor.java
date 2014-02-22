package cert.forensics.liveview;

/*
 * This is a thread used to consume the output of an external process in
 * a separate thread to avoid conflicts/deadlock and for performance reasons.
 *
 * Author: 	Brian Kaplan
 * 			bfkaplan@cmu.edu
 * 
 * Copyright (C) 2006  Carnegie Mellon University
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 * 
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ProcessStreamProcessor extends Thread
{
	private InputStream		is;
	private StringBuffer	returnText;

	public ProcessStreamProcessor(InputStream is)
	{
		this.is = is;
		returnText = new StringBuffer();
	}

	public void run()
	{
		try
		{
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			String line = null;

			while ((line = reader.readLine()) != null)	//add to buffer line by line
			{
				if(!line.trim().equals(""))		//skip blank lines
					returnText.append(line + System.getProperty("line.separator"));
			}
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
		}

	}

	/*
	 * Return the stdout buffer
	 */
	public String getReturnText()
	{
		return returnText.toString();
	}

}
