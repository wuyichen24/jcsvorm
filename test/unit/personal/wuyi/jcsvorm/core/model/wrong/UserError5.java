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

import personal.wuyi.jcsvorm.annotation.CsvColumn;
import personal.wuyi.jcsvorm.annotation.CsvEntity;

@CsvEntity
public class UserError5 {
	@CsvColumn              // no name or pos parameter
	private String name;
	
	@CsvColumn
	private int salary;
	
	@CsvColumn
	private Date dob;
	
	@CsvColumn
	private double performance;
	
	@CsvColumn
	private boolean isHealth;

	public String  getName()                          { return name;                    }
	public void    setName(String name)               { this.name = name;               }
	public int     getSalary()                        { return salary;                  }
	public void    setSalary(int salary)              { this.salary = salary;           }
	public Date    getDob()                           { return dob;                     }
	public void    setDob(Date dob)                   { this.dob = dob;                 }
	public double  getPerformance()                   { return performance;             }
	public void    setPerformance(double performance) { this.performance = performance; }
	public boolean isHealth()                         { return isHealth;                }
	public void    setHealth(boolean isHealth)        { this.isHealth = isHealth;       }
}
