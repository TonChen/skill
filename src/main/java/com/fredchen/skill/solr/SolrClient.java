package com.fredchen.skill.solr;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
    
public class SolrClient {

    private static final Logger LOG = LoggerFactory.getLogger(SolrClient.class);
    
    private static Map<String, SolrServer> solrServerMap = Collections.synchronizedMap(new HashMap<String, SolrServer>());
    
    /**
     * 获取HttpSolrServer
     * 
     * @param SOLR_URL
     * 
     * @return SolrServer
     */
    public static SolrServer getHttpSolrServer(final String solrURL) {
        SolrServer solrServer = null;
        if (!solrServerMap.containsKey(solrURL)) {
            try {
                solrServer = new HttpSolrServer(solrURL);
                if (solrServer != null) {
                    solrServerMap.put(solrURL, solrServer);
                    LOG.info("Load " + solrURL + " finish.");
                }
            } catch (Exception e) {
                LOG.warn("sorlURL error ," + solrURL);
                e.printStackTrace();
            }
        }
        return solrServerMap.get(solrURL);
    }
    
    /**
     * ping检测solr是否down掉 [测试通过]
     * @param server
     * @return
     */
    public static String ping(SolrServer server){
        try {
            return server.ping().getResponse().toString();
        } catch (SolrServerException e) {
            LOG.error("Solr system ping error " + e.getMessage(), e);
        } catch (IOException e) {
            LOG.error("Solr system ping error " + e.getMessage(), e);
        }
        return null;
    }
    
    public static void main(String[] args) {
    	String solrURL = "http://120.26.132.15:8983/"; 
    	SolrServer server = SolrClient.getHttpSolrServer(solrURL);
    	System.out.println("ping solr result: " +ping(server));
        
	}
}