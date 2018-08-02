# jcsvorm

[![Build Status](https://travis-ci.org/wuyichen24/jcsvorm.svg?branch=master)](https://travis-ci.org/wuyichen24/jcsvorm)
[![Coverage Status](https://coveralls.io/repos/github/wuyichen24/jcsvorm/badge.svg?branch=master)](https://coveralls.io/github/wuyichen24/jcsvorm?branch=master)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/e867f61a668a490695a6db0245b85600)](https://www.codacy.com/project/wuyichen24/jcsvorm/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=wuyichen24/jcsvorm&amp;utm_campaign=Badge_Grade_Dashboard)
[![License](https://img.shields.io/badge/License-Apache%202.0-green.svg)](https://opensource.org/licenses/Apache-2.0) 

Use annotation and reflection to handle the ORM (Object-Relational Mapping) between Java classes and CSV (Comma-Separated Values) files.

## Overview
A comma-separated values (CSV) file is a delimited text file that uses a comma to separate values. The structure of CSV file content is table which each line is a data record and it has one or more columns, which is very similar to a table in relational database. In java, there is the Hibernate framework for mapping an object-oriented domain model to a relational database. So why don't we have a framework which can map an object-oriented domain model to a CSV file.

In jcsvorm, it uses *@CsvColumn* with parameter *name* to handle the mapping between a field in java and a column in CSV file, like

**In Java**
```java
@CsvColumn(name = "first name")
private String firstName;
```

**In CSV**

| first name |
| - |
| Jack |
| Mary |
| ...  |

If your CSV file doesn't have the header line, you can also use the column index to map, like
```java
@CsvColumn(pos = 0)
private String firstName;
```

If a column is date format in CSV, you can use *format* parameter to parse or write a column, like

**In Java**
```java
@CsvColumn(pos = "DOB", format="yyyy-MM-dd")
private Date   dob;
```

**In CSV**

| DOB |
| - |
| 1978-02-17 |
| 1984-11-26 |
| ...  |

After using @CsvColumn with different parameters, it will make much easier for reading and writing a CSV, just one line function call per each functionality, like:

**Read**
```java
List<Student> studentList = CsvFactory.readCsv(Student.class, "data/data_with_header.csv", HeaderOption.WITH_HEADER);
```
**Write**
```java
CsvFactory.writeCsv(studentList, "data/output.csv", HeaderOption.WITH_HEADER);
```

## Getting Started
Please see our [Wiki](https://github.com/wuyichen24/jcsvorm/wiki/Getting-Started) page.

## Documentation
Please see our [Wiki](https://github.com/wuyichen24/jcsvorm/wiki) page.

## Download
- [Download ZIP](https://github.com/wuyichen24/jcsvorm/archive/master.zip)
- [Download JAR](https://github.com/wuyichen24/jcsvorm/releases/download/v1.1/jcsvorm-1.1.jar)

## Contributing

## License
[Apache-2.0](https://opensource.org/licenses/Apache-2.0)

## Authors
- **[Wuyi Chen](https://www.linkedin.com/in/wuyichen24/)**
