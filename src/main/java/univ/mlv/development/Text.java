/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package univ.mlv.development;

/**
 *
 * @author fadhela-pc
 */
public class Text {
    public String id="";
        public String content="";
        public String rdf="";
        public String source="";
        public String author= "";
        public String dateRef="";
    public Text(String id,String content,String rdf,String source,String author,String dateRef){
         this.id=id;
        this.content=content;
         this.rdf=rdf;
         this.source=source;
         this.author= author;
         this.dateRef=dateRef;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return the rdf
     */
    public String getRdf() {
        return rdf;
    }

    /**
     * @param rdf the rdf to set
     */
    public void setRdf(String rdf) {
        this.rdf = rdf;
    }

    /**
     * @return the source
     */
    public String getSource() {
        return source;
    }

    /**
     * @param source the source to set
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * @return the author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * @param author the author to set
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * @return the dateRef
     */
    public String getDateRef() {
        return dateRef;
    }

    /**
     * @param dateRef the dateRef to set
     */
    public void setDateRef(String dateRef) {
        this.dateRef = dateRef;
    }
}
