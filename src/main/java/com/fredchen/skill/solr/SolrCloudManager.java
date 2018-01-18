package com.fredchen.skill.solr;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

import com.cfth.chan.model.GoodsBrand;
  
/** 
 * SolrCloud 索引增删查测试 
 * @author ziyuzhang 
 * 
 */  
public class SolrCloudManager {         
    private static CloudSolrServer cloudSolrServer;    
        
    private  static synchronized CloudSolrServer getCloudSolrServer(final String zkHost) throws MalformedURLException {    
        if(cloudSolrServer == null) {    
            try {    
                cloudSolrServer = new CloudSolrServer(zkHost);    
            }catch(Exception e) {    
                e.printStackTrace();                    
            }    
        }    
            
        return cloudSolrServer;    
    }    
        
    private void addIndex(SolrServer solrServer) {          
        try {    
            SolrInputDocument doc1 = new SolrInputDocument();    
            doc1.addField("id", "421245251215121452521251");    
            doc1.addField("brand_id", "421245251215121452521251");    
            doc1.addField("brand_name", "北京品牌");    
            SolrInputDocument doc2 = new SolrInputDocument();    
            doc2.addField("id", "4224558524254245848524243");    
            doc2.addField("brand_id", "4224558524254245848524243");    
            doc2.addField("brand_name", "上海品牌");   
            
            SolrInputDocument doc3 = new SolrInputDocument();    
            doc3.addField("id", "4543543458643541324153453");    
            doc3.addField("brand_id", "4543543458643541324153453");    
            doc3.addField("brand_name", "重庆品牌");    
                
            Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();    
            docs.add(doc1);    
            docs.add(doc2);    
            docs.add(doc3);    
      
            solrServer.add(docs);               
            solrServer.commit();    
                
        }catch(SolrServerException e) {    
            System.out.println("Add docs Exception !!!");    
            e.printStackTrace();            
        }catch(IOException e){    
            e.printStackTrace();    
        }catch (Exception e) {    
            System.out.println("Unknowned Exception!!!!!");    
            e.printStackTrace();    
        }           
    }    
        
    
    private void updateIndex(SolrServer solrServer) {          
        try {    
//        	SolrInputDocument doc1 = solrServer.getBinder().toSolrInputDocument(new GoodsBrand());
            SolrInputDocument doc1 = new SolrInputDocument();  
            doc1.addField("id", "421245251215121452521251");    
            doc1.addField("brand_id", "421245251215121452521251");    
            doc1.addField("brand_name", "北京品牌0");    
            
            SolrInputDocument doc2 = new SolrInputDocument();    
            doc2.addField("id", "4224558524254245848524243");    
            doc2.addField("brand_id", "422455852425424584852424311");    
            doc2.addField("brand_name", "上海品牌0");   
            
            SolrInputDocument doc3 = new SolrInputDocument();    
            doc3.addField("id", "4543543458643541324153453");    
            doc3.addField("brand_id", "454354345864354132415345311");    
            doc3.addField("brand_name", "重庆品牌0");    
                
            Collection<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();    
            docs.add(doc1);    
            docs.add(doc2);    
            docs.add(doc3);    
            solrServer.add(docs);               
            solrServer.commit();    
                
        }catch(SolrServerException e) {    
            System.out.println("Add docs Exception !!!");    
            e.printStackTrace();            
        }catch(IOException e){    
            e.printStackTrace();    
        }catch (Exception e) {    
            System.out.println("Unknowned Exception!!!!!");    
            e.printStackTrace();    
        }           
            
    } 
    
    public void search(SolrServer solrServer, String String) {          
        SolrQuery query = new SolrQuery();    
        query.setQuery(String);    
        try {    
            QueryResponse response = solrServer.query(query);    
            SolrDocumentList docs = response.getResults();    
    
            System.out.println("文档个数：" + docs.getNumFound());    
            System.out.println("查询时间：" + response.getQTime());    
    
            for (SolrDocument doc : docs) {    
                String area = (String) doc.getFieldValue("brand_name");    
                String id = (String) doc.getFieldValue("brand_id");    
                System.out.println("id: " + id);    
                System.out.println("area: " + area);    
                System.out.println();    
            }    
        } catch (SolrServerException e) {    
            e.printStackTrace();    
        } catch(Exception e) {    
            System.out.println("Unknowned Exception!!!!");    
            e.printStackTrace();    
        }    
    }    
        
    public void deleteAllIndex(SolrServer solrServer) {    
        try {    
            solrServer.deleteByQuery("*:*");// delete everything!    
            solrServer.commit();    
        }catch(SolrServerException e){    
            e.printStackTrace();    
        }catch(IOException e) {    
            e.printStackTrace();    
        }catch(Exception e) {    
            System.out.println("Unknowned Exception !!!!");    
            e.printStackTrace();    
        }    
    }    
        
    /**  
     * @param args  
     * @throws MalformedURLException 
     */    
    public static void main(String[] args) throws MalformedURLException {      
            final String zkHost = "121.40.148.172:2181,121.40.101.86:2181,120.26.132.15:2181";         
            final String  defaultCollection = "collection1";    
            final int  zkClientTimeout = 20000;    
            final int zkConnectTimeout = 10000;    
                
            CloudSolrServer cloudSolrServer = getCloudSolrServer(zkHost);           
            System.out.println("The Cloud SolrServer Instance has benn created!");              
            cloudSolrServer.setDefaultCollection(defaultCollection);  
            cloudSolrServer.setZkClientTimeout(zkClientTimeout);    
            cloudSolrServer.setZkConnectTimeout(zkConnectTimeout);                     
            cloudSolrServer.connect();    
            System.out.println("The cloud Server has been connected !!!!");              
            //测试实例！    
            SolrCloudManager test = new SolrCloudManager();
//            System.out.println("测试添加index！！！");         
//            添加index    
//            test.addIndex(cloudSolrServer);    
                
            System.out.println("测试查询query！！！！");    
            test.search(cloudSolrServer, "brand_name:*");    
//              test.updateIndex(cloudSolrServer);  
    //        System.out.println("测试删除！！！！");    
    //        test.deleteAllIndex(cloudSolrServer);    
    //        System.out.println("删除所有文档后的查询结果：");    
//            test.search(cloudSolrServer, "zhan");        
    //        System.out.println("hashCode"+test.hashCode());  
                        
             // release the resource     
            cloudSolrServer.shutdown();    
     
    }    
    
}  