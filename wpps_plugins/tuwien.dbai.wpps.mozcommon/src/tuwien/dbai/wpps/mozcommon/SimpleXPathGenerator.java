/**
 * File name: SimpleXPathGenerator.java
 * @created: Oct 7, 2011 3:47:19 PM
 * @author Ruslan (ruslanrf@gmail.com)
 */

package tuwien.dbai.wpps.mozcommon;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;
import org.mozilla.interfaces.nsIDOMElement;
import org.mozilla.interfaces.nsIDOMNode;
import org.mozilla.interfaces.nsIDOMNodeList;

import tuwien.dbai.wpps.common.exceptions.UnappropriateValueOfArgument;

/**
 * @created: Oct 7, 2011 3:47:19 PM
 * @author Ruslan (ruslanrf@gmail.com)
 */
public final class SimpleXPathGenerator {
	private static final Logger log = Logger.getLogger(SimpleXPathGenerator.class);
	
	public static final String SHOW_CLASS = "showClass";
	public static final String SHOW_ID = "showId";
	public static final String SHOW_NS = "showNS";
	public static final String DEFAULT_NS = "default";
	public static final String TO_LOWER_CASE = "toLowercase";

	 /* XPather,  Author: Viktor Zigo, http://xpath.alephzarro.com */
	private static String walkUp(nsIDOMNode node, int depth, int maxDepth, nsIDOMNode aSentinel, boolean aDefaultNS, Set<String> kwds)
	{
	    //log(depth+" node:"+node.nodeName +" aSentinel:"+aSentinel.nodeName);
	    String str = "";
	    if (node == null)
	    	throw new UnappropriateValueOfArgument(log, node);
//	    if (node == null) {
//	        throw new NullPointerException();
//	      }
	    
	    if(node.equals(aSentinel)) return ".";
	    if((node.getParentNode() != null) && ( (maxDepth<0) || (depth < maxDepth))  ) {
	        str += walkUp(node.getParentNode(), depth + 1, maxDepth, aSentinel, aDefaultNS, kwds);
	    }
	    //log(node+'  '+node.nodeName +'  type:'+node.nodeType+ ' exp:'+Node.ELEMENT_NODE);
	    switch (node.getNodeType()) {
	        case nsIDOMNode.ELEMENT_NODE:{
	                String nname = node.getLocalName();
	                final Set<String> conditions = new HashSet<String>();
	                boolean hasid = false;
	                nsIDOMElement el = (nsIDOMElement)node.queryInterface(nsIDOMElement.NS_IDOMELEMENT_IID);
	                if (kwds.contains(SHOW_CLASS) && el.hasAttribute("class"))
	                	conditions.add("@class='"+el.getAttribute("class")+"'");
	                if (kwds.contains(SHOW_ID) && el.hasAttribute("id")) {
	                    conditions.add("@id='"+el.getAttribute("id")+"'");
	                    hasid = true;
	                }
	                    
	                //not identified by id?
	                if(!hasid){
	                    final int index = siblingIndex(node);
	                    //more than one sibling?
	                    if (index>0) {
	                        //are there also other conditions?
	                        if (conditions.size()>0) conditions.add("position()="+index);
	                        else conditions.add(String.valueOf(index));
	                    }
	    
	                }
	                if (kwds.contains(SHOW_NS)){
	                    if(node.getPrefix() != null) nname=node.getPrefix()+":"+nname;
	                    else if (aDefaultNS) nname=DEFAULT_NS+":"+nname;
	                }
	                if (kwds.contains(TO_LOWER_CASE))
	                	nname=nname.toLowerCase();
	                
	                str += "/"+nname;
	                
	                if(conditions.size()>0){
	                    str+="[";
	                    Iterator<String> iter = conditions.iterator();
	                    boolean firstIter = true;
	                    while (iter.hasNext()) {
	                    	if (firstIter) firstIter = false;
	                    	else {str+=" and "; }
	                    	str+=iter.next();
	                    }
	                    str+="]";
	                }
	                break;
	            }
	        case nsIDOMNode.DOCUMENT_NODE:{
	            break;
	        }
	        case nsIDOMNode.TEXT_NODE:{
	            //str='string('+str+')';
	            str+="/text()";
	            final int index = siblingIndex(node);
	            if (index>0) str+="["+index+"]";
	            break;
	        }
	        
	    }
	    return str;            
	}
	
	/**
	 * gets index of aNode (relative to other same-tag siblings)
	 * first position = 1; returns null if the component is the only one
	 * @param aNode
	 * @return
	 */
	private static int siblingIndex(nsIDOMNode aNode){
		final nsIDOMNodeList siblings = aNode.getParentNode().getChildNodes();
	    int allCount = 0;
	    int position = -1;

	    if (aNode.getNodeType()==nsIDOMNode.ELEMENT_NODE){
	        final String name = aNode.getNodeName();
	        for (int i=0; i<siblings.getLength(); i++){
	        	final nsIDOMNode node = siblings.item(i);
	            if (node.getNodeType()==nsIDOMNode.ELEMENT_NODE){
	                if (node.getNodeName().equals(name))
	                	allCount++;  //nodeName includes namespace
	                if (node.equals(aNode))
	                	position = allCount;
	            }
	        }
	    }
	    else if (aNode.getNodeType()==nsIDOMNode.TEXT_NODE){
	        for (int i=0; i<siblings.getLength(); i++){
	        	final nsIDOMNode node = siblings.item(i);
	            if (node.getNodeType()==nsIDOMNode.TEXT_NODE){
	                allCount++;
	                if (node.equals(aNode))
	                	position = allCount;
	            }
	        }
	    }
    	return position;
	}
	
	private static Set<String> getDefaultParams() {
		Set<String> kwds = new HashSet<String>();
		kwds.add(SHOW_ID);
//		kwds.add(SHOW_CLASS);
		return kwds;
	}
	
	public static final String getXPath(nsIDOMNode aNode) {
	    return walkUp(aNode,0,-1, null, false, getDefaultParams());
	}
	
	
}



