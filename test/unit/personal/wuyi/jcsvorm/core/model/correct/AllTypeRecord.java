/*
 * Copyright 2018 Wuyi Chen.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package personal.wuyi.jcsvorm.core.model.correct;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;

import personal.wuyi.jcsvorm.annotation.CsvColumn;
import personal.wuyi.jcsvorm.annotation.CsvEntity;

@CsvEntity
public class AllTypeRecord {
	@CsvColumn(name = "string_value")                               private String         stringValue;
	@CsvColumn(name = "integer_value")                              private int            integerValue;
	@CsvColumn(name = "long_value")                                 private long           longValue;
	@CsvColumn(name = "double_value")                               private double         doubleValue;
	@CsvColumn(name = "float_value")                                private float          floatValue;
	@CsvColumn(name = "short_value")                                private short          shortValue;
	@CsvColumn(name = "char_value")                                 private char           charValue;
	@CsvColumn(name = "boolean_value")                              private boolean        booleanValue;
	@CsvColumn(name = "util_date_value",       format="yyyy-MM-dd") private java.util.Date utilDateValue;
	@CsvColumn(name = "timestamp_value",       format="yyyy-MM-dd") private Timestamp      timestampValue;
	@CsvColumn(name = "calendar_value",        format="yyyy-MM-dd") private Calendar       calendarValue;
	@CsvColumn(name = "sql_date_value",        format="yyyy-MM-dd") private java.sql.Date  sqlDateValue;
	@CsvColumn(name = "local_date_value",      format="yyyy-MM-dd") private LocalDate      localDateValue;
	@CsvColumn(name = "local_date_time_value", format="yyyy-MM-dd_HH:mm") private LocalDateTime  localDateTimeValue;
	
	public String         getStringValue()                                        { return stringValue;                           }
	public void           setStringValue(String stringValue)                      { this.stringValue = stringValue;               }
	public int            getIntegerValue()                                       { return integerValue;                          }
	public void           setIntegerValue(int integerValue)                       { this.integerValue = integerValue;             }
	public long           getLongValue()                                          { return longValue;                             }
	public void           setLongValue(long longValue)                            { this.longValue = longValue;                   }
	public double         getDoubleValue()                                        { return doubleValue;                           }
	public void           setDoubleValue(double doubleValue)                      { this.doubleValue = doubleValue;               }
	public float          getFloatValue()                                         { return floatValue;                            }
	public void           setFloatValue(float floatValue)                         { this.floatValue = floatValue;                 }
	public short          getShortValue()                                         { return shortValue;                            }
	public void           setShortValue(short shortValue)                         { this.shortValue = shortValue;                 }
	public char           getCharValue()                                          { return charValue;                             }
	public void           setCharValue(char charValue)                            { this.charValue = charValue;                   }
	public boolean        getBooleanValue()                                       { return booleanValue;                          }
	public void           setBooleanValue(boolean booleanValue)                   { this.booleanValue = booleanValue;             }
	public java.util.Date getUtilDateValue()                                      { return utilDateValue;                         }
	public void           setUtilDateValue(java.util.Date utilDateValue)          { this.utilDateValue = utilDateValue;           }
	public Timestamp      getTimestampValue()                                     { return timestampValue;                        }
	public void           setTimestampValue(Timestamp timestampValue)             { this.timestampValue = timestampValue;         }
	public Calendar       getCalendarValue()                                      { return calendarValue;                         }
	public void           setCalendarValue(Calendar calendarValue)                { this.calendarValue = calendarValue;           }
	public java.sql.Date  getSqlDateValue()                                       { return sqlDateValue;                          }
	public void           setSqlDateValue(java.sql.Date sqlDateValue)             { this.sqlDateValue = sqlDateValue;             }
	public LocalDate      getLocalDateValue()                                     { return localDateValue;                        }
	public void           setLocalDateValue(LocalDate localDateValue)             { this.localDateValue = localDateValue;         }
	public LocalDateTime  getLocalDateTimeValue()                                 { return localDateTimeValue;                    }
	public void           setLocalDateTimeValue(LocalDateTime localDateTimeValue) { this.localDateTimeValue = localDateTimeValue; }
	
	
}
