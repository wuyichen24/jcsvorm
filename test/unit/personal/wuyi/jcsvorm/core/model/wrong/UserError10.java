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

package personal.wuyi.jcsvorm.core.model.wrong;

import java.util.Date;
import java.util.regex.Pattern;

import personal.wuyi.jcsvorm.annotation.CsvColumn;
import personal.wuyi.jcsvorm.annotation.CsvEntity;

@CsvEntity
public class UserError10 {
	@CsvColumn(name = "name")
	private String name;
	
	@CsvColumn(name = "int_amount")
	private int    salary;
	
	@CsvColumn(name = "date", format="yyyy-MM-dd")
	private Date   dob;
	
	@CsvColumn(name = "double_amount")
	private double performance;
	
	@CsvColumn(name = "health")
	private Pattern pattern;   // not an easy type
	
	public UserError10() {
		
	}
	
	public UserError10(String name, int salary, Date dob, double performance, Pattern pattern) {
		this.name = name;
		this.salary = salary;
		this.dob = dob;
		this.performance = performance;
		this.pattern = pattern;
	}

	public String       getName()                          { return name;                    }
	public void         setName(String name)               { this.name = name;               }
	public int          getSalary()                        { return salary;                  }
	public void         setSalary(int salary)              { this.salary = salary;           }
	public Date         getDob()                           { return dob;                     }
	public void         setDob(Date dob)                   { this.dob = dob;                 }
	public double       getPerformance()                   { return performance;             }
	public void         setPerformance(double performance) { this.performance = performance; }
	public Pattern      getPattern()                       { return pattern;                 }
	public void         setPattern(Pattern pattern)        { this.pattern = pattern;         }

	
}
