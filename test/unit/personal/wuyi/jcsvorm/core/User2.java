package personal.wuyi.jcsvorm.core;

import java.util.Date;

import personal.wuyi.jcsvorm.annotation.CsvColumn;
import personal.wuyi.jcsvorm.annotation.CsvEntity;

@CsvEntity
public class User2 {
	@CsvColumn(pos = 0)
	private String name;
	
	@CsvColumn(pos = 1)
	private int    salary;
	
	@CsvColumn(pos = 2, format="yyyy-MM-dd")
	private Date   dob;
	
	@CsvColumn(pos = 3)
	private double performance;
	
	@CsvColumn(pos = 4)
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
