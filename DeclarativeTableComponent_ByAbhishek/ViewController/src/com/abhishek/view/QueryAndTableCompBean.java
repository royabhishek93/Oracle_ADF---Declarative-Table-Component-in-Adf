package com.abhishek.view;


import javax.faces.event.ActionEvent;
import java.util.HashMap;



import oracle.adf.model.BindingContext;
import oracle.adf.model.binding.DCBindingContainer;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.logging.ADFLogger;



import oracle.adfinternal.view.faces.model.binding.FacesCtrlHierDef;


import oracle.jbo.uicli.binding.JUCtrlHierBinding;
import oracle.jbo.uicli.binding.JUCtrlHierDef;
import oracle.jbo.uicli.binding.JUCtrlHierTypeBinding;
import oracle.jbo.uicli.binding.JUIteratorBinding;
import oracle.jbo.uicli.binding.JUIteratorDef;
import oracle.jbo.uicli.mom.JUTags;

public class QueryAndTableCompBean {
    public QueryAndTableCompBean() {
    }

    private static final ADFLogger log=ADFLogger.createADFLogger(QueryAndTableCompBean.class);

    
      public DCIteratorBinding getIterator(String iteratorName, String voName,  
          String rsiName) {  
          DCBindingContainer dbc = ADFUtils.getDCBindingContainer();
          DCIteratorBinding iter = dbc.findIteratorBinding(iteratorName);  
        
          if (iter == null) {   
              JUIteratorDef juDef =  
                      new JUIteratorDef(iteratorName, null, voName, rsiName, 25);  
              HashMap initValues = new HashMap();  
              initValues.put(JUTags.DataControl, getDataControlName());  
              juDef.init(initValues);  
              iter = juDef.createIterBinding(BindingContext.getCurrent(), dbc);  
              dbc.addIteratorBinding(iteratorName, iter);  
          }  
          return iter;  
        
      }  
        
        
      public JUCtrlHierBinding getTree() { 
         DCBindingContainer dbc = ADFUtils.getDCBindingContainer();
         JUCtrlHierBinding juchb =   
           (JUCtrlHierBinding)dbc.findCtrlBinding(getTreeName());  
        
          if (juchb == null) { 
              JUIteratorBinding iter =  
               (JUIteratorBinding)getIterator(getIteratorName(), getVOName(), null);  
             JUCtrlHierDef jucDef = new FacesCtrlHierDef(); 
              HashMap initValues = new HashMap();  
              initValues.put(JUCtrlHierDef.PNAME_IterBinding, iter.getName());  
              JUCtrlHierTypeBinding typeBinding = new JUCtrlHierTypeBinding();  
              initValues.put(JUCtrlHierDef.PNAME_TypeBindings,  
                             new JUCtrlHierTypeBinding[] { typeBinding });  
              jucDef.init(initValues);  
              juchb = (JUCtrlHierBinding)jucDef.createControlBinding(dbc);  
              dbc.addControlBinding(getTreeName(), juchb);  
          }  
          return juchb;  
      } 
      
      public String getTreeName(){
          System.out.println("getDataControlName--"+(String)JSFUtils.resolveExpression("#{attrs.voName}"));
          log.info("Tree Name===>"+(String)JSFUtils.resolveExpression("#{attrs.voName}"));
          return (String)JSFUtils.resolveExpression("#{attrs.voName}"); 
      }
      public String getIteratorName(){
          System.out.println("getDataControlName--"+(String)JSFUtils.resolveExpression("#{attrs.voName}")+"Iterator");
       log.info("Iteratoir Name===>"+(String)JSFUtils.resolveExpression("#{attrs.voName}")+"Iterator");
          return (String)JSFUtils.resolveExpression("#{attrs.voName}")+"Iterator";
      }
      public String getVOName(){
          System.out.println("getDataControlName--"+(String)JSFUtils.resolveExpression("#{attrs.voName}"));
        log.info("VO Name===>"+(String)JSFUtils.resolveExpression("#{attrs.voName}"));
          return (String)JSFUtils.resolveExpression("#{attrs.voName}");
      }
      
      public String getDataControlName(){
          System.out.println("getDataControlName--"+(String)JSFUtils.resolveExpression("#{attrs.dcName}"));
        log.info("Data Control Name====>"+(String)JSFUtils.resolveExpression("#{attrs.dcName}"));
          return (String)JSFUtils.resolveExpression("#{attrs.dcName}");
      }
      
      public Long getRowCountEstimated(){
         return ADFUtils.findIterator(getIteratorName()).getEstimatedRowCount();
      }
}
