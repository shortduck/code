package gov.michigan.dit.timeexpense.util;

import java.util.List;

public interface ExpenseComparator extends Comparable<Object>{
	
	public List<Object> sort(List<Object> obj_expenseList,String colName,boolean sortOrder);
	public int compareTo(Object obj_expenseData);

}
