/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package univ.mlv.development;
import java.util.Iterator;
import java.util.List;

import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QueryParseException;
import com.hp.hpl.jena.sparql.algebra.Algebra;
import com.hp.hpl.jena.sparql.algebra.Op;
import com.hp.hpl.jena.sparql.algebra.OpAsQuery;
import com.hp.hpl.jena.sparql.core.TriplePath;
import com.hp.hpl.jena.sparql.core.Var;
import com.hp.hpl.jena.sparql.lang.Parser;
import com.hp.hpl.jena.sparql.syntax.Element;
import com.hp.hpl.jena.sparql.syntax.ElementPathBlock;
import com.hp.hpl.jena.sparql.syntax.ElementVisitorBase;
import com.hp.hpl.jena.sparql.syntax.ElementWalker;
/**
 *
 * @author fadhela-pc
 */
public class ParserJena extends Parser {
	
	private Query arqQuery;

	public ParserJena(String query) {
//            super();
            
//		super(query);
		try {
			arqQuery = QueryFactory.create(query);
		} catch (QueryParseException e) {
			arqQuery=null;
		}
	}
	public Query getArqQuery() {
		return arqQuery;
	}
	public boolean isSelect() {
		if(arqQuery==null)
			return false;
		else
			return arqQuery.isSelectType();
	}
	
	public void parse() {
		if(isSelect()) {
			Op op = Algebra.compile(arqQuery);
			
			Query query = OpAsQuery.asQuery(op);
			List<Var> varList = query.getProject().getVars();
			for(Var var : varList) {
//				parsedQuery.addDistinguishedVar(var);
			}
			Element queryPattern =query.getQueryPattern();
			
			ElementVisitorBase elementVisitor = new ElementVisitorBase() {
				@Override
				public void visit(ElementPathBlock el) {
				Iterator<TriplePath> iterator = el.getPattern().iterator();
				while (iterator.hasNext()) {
					TriplePath triplePath = iterator.next();
//					parsedQuery.addTriple(new Triple(triplePath.getSubject(),triplePath.getPredicate(),triplePath.getObject()));
				}
				super.visit(el);
				}
			};
			ElementWalker.walk(queryPattern, elementVisitor);				
		}
	}

    @Override
    public Query parse(Query query, String string) throws QueryParseException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
	
}