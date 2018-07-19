package personal.wuyi.jcsvorm.core;

import java.util.Date;

import personal.wuyi.jcsvorm.annotation.CsvColumn;
import personal.wuyi.jcsvorm.annotation.CsvEntity;

@CsvEntity
public class User4 {
	@CsvColumn(name = "name", pos = 0)
	private String name;
	
	@CsvColumn(name = "int_amount", pos = 1)
	private int    salary;
	
	@CsvColumn(name = "dob", format="yyyy-MM-dd", pos = 2)
	private Date   dob;
	
	@CsvColumn(name = "perf_balance", pos = 3)
	private double performance;
	
	@CsvColumn(name = "health", pos = 4)
	private boolean isHealth;

	public User4(String name, int salary, Date dob, double performance, boolean health) {
		this.name = name;
		this.salary = salary;
		this.dob = dob;
		this.performance = performance;
		this.isHealth = health;
	}
	
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
