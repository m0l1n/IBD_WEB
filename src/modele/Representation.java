package modele;
import java.util.Calendar;
import java.util.Date;
import java.sql.Time;

//classe mettant en place les representation

public class Representation {

    private Date date;
    private int num ;
    public Calendar calendar;
    
    public Representation (int n, Date d) {
                this.date = d;
                calendar= Calendar.getInstance();
                calendar.setTime(d);
                num=n;
    }

    public Representation (int n, Date d,Time t) {
                this.date = d;
                calendar= Calendar.getInstance();
                Calendar tmp=Calendar.getInstance();
                calendar.setTime(d);
                tmp.setTime(t);
                calendar.set(Calendar.HOUR_OF_DAY,tmp.get(Calendar.HOUR_OF_DAY));
                num=n;
    }

    public int  getNum () {
        return this.num;
    }
   
 
    public Date getDate () {
        return this.date;
    }
   
   
   
    public void setNum (int n) {
        this.num= n;
    }
   
    public void setDate (Date d) {
        this.date = d;
    }

    public String toString(){
        String rslt="";
        rslt=calendar.get(Calendar.DAY_OF_MONTH)+"/"+(calendar.get(Calendar.MONTH)+1)+"/"+calendar.get(Calendar.YEAR)+"  "+calendar.get(Calendar.HOUR_OF_DAY)+"h";
        return rslt;
    }
   
   
}
