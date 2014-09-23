/*
 *  $Id$
 *
 *  This file is part of the OpenLink Software Virtuoso Open-Source (VOS)
 *  project.
 *
 *  Copyright (C) 1998-2014 OpenLink Software
 *
 *  This project is free software; you can redistribute it and/or modify it
 *  under the terms of the GNU General Public License as published by the
 *  Free Software Foundation; only version 2 of the License, dated June 1991.
 *
 *  This program is distributed in the hope that it will be useful, but
 *  WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 *  General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along
 *  with this program; if not, write to the Free Software Foundation, Inc.,
 *  51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
 *
 */

package virtuoso.jdbc2;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.sql.*;

public class VirtuosoTime extends java.sql.Time 
{
    int timezone = 0;
    boolean with_timezone = false;

    public VirtuosoTime(long time) {
	super(time);
    }

    public VirtuosoTime(long time, int tz) {
	super(time);
	this.timezone = tz;
	this.with_timezone = true;
    }

    public VirtuosoTime(long time, int tz, boolean with_tz) {
	super(time);
	this.timezone = tz;
	this.with_timezone = with_tz;
    }


    public boolean withTimezone()
    {
    	return this.with_timezone;
    }

    public int getTimezone()
    {
        return this.timezone;
    }

    public VirtuosoTime clone()
    {
        return new VirtuosoTime(getTime(), timezone, with_timezone);
    }

    public String toXSD_String ()
    {
        StringBuilder sb = new StringBuilder();
        DateFormat formatter= new SimpleDateFormat("HH:mm:ss");
        String timeZoneString = null;

        if (with_timezone)
        {
            StringBuffer s = new StringBuffer();
            s.append(timezone>0?'+':'-');

            int tz = Math.abs(timezone);
            int tzh = tz/60;
            int tzm = tz%60;

            if (tzh < 10)
                s.append('0');

            s.append(tzh);
            s.append(':');

            if (tzm < 10)
                s.append('0');

            s.append(tzm);
            timeZoneString = s.toString();
            formatter.setTimeZone(TimeZone.getTimeZone("GMT"+timeZoneString));
        }
        sb.append(formatter.format(this));

        if (timeZoneString!=null)
            sb.append(timeZoneString);
        return sb.toString();
    }
}

