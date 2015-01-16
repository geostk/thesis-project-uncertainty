/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package univ.mlv.Structures;

/**
 *
 * @author fadhela-pc
 */
public class Triples {
    public static String subject;
    public static String predicate;
    public static String object;
    public static String num;
    /**
     * @return the subject
     */
    public static String getSubject() {
        return subject;
    }

    /**
     * @param aSubject the subject to set
     */
    public static void setSubject(String aSubject) {
        subject = aSubject;
    }

    /**
     * @return the predicate
     */
    public static String getPredicate() {
        return predicate;
    }

    /**
     * @param aPredicate the predicate to set
     */
    public static void setPredicate(String aPredicate) {
        predicate = aPredicate;
    }

    /**
     * @return the object
     */
    public static String getObject() {
        return object;
    }

    /**
     * @param aObject the object to set
     */
    public static void setObject(String aObject) {
        object = aObject;
    }
    
    public Triples(String triplet, String num){
        String[] split = triplet.split(" ");
        this.subject=split[0];
        this.predicate=split[1];
        this.object=split[2];
        this.num= num;
    }
    /**
     * s-p-o
     * @return 
     */
    public String caseO(){
        return "Optional {"
                + getSubject()+" "+getPredicate()+" "+getObject()+".\n"
                + "}";
        
    }
    /**
     * u->s-o-p
     * @return 
     */
    public String case1(){
        return "Optional {"
                + "?u gs:isUncertain "+getSubject()+".\n"
                + "?u gs:weight ?weightS"+num+".\n"
                + getSubject()+" "+getPredicate()+" "+getObject()+".\n"
                + "}";
        
    }
    /**
     * s->u-p-o
     * @return 
     */
    public String case2(){
        return"Optional {"
                + getSubject()+" gs:hasUncertainProp ?u.\n"
                + "?u "+getPredicate()+" "+getObject()+".\n"
                + "?u gs:weight ?weightP"+num+".\n"
                + "}";
        
    }
    
    public String case3(){
        return "Optional {"
                + getSubject()+" "+getPredicate()+ " "+getObject()+".\n" 
                + "?u gs:isUncertain "+getObject()+".\n"
                + "?u gs:weight ?weightO"+num+".\n"
                + "}";
    }
    public String addUncertOnSub(){
        String res="";
        res = "Optional {"
                + "?u gs:isUncertain "+getSubject()+".\n"
                + "?u gs:weight ?weightC.\n"
                + "}";
        return res;
    }
    
    public String addUncertOnObj(){
        String res="";
        res = "Optional {"
                + "?u gs:isUncertain "+getObject()+"."
                + "?u gs:weight ?weightO."
                + "}";
        return res;
    }
    
    public String addUncertOnProp(){
        String res="";
        res = "Optional {"
                + getSubject()+" gs:hasUncertainProp ?u"
                + "?u "+getPredicate()+" "+ getObject()+"."
                + "?u gs:weight ?weightP."
                + "}";
        return res;
    }
}
