package sg.edu.iss.jinder.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import sg.edu.iss.jinder.model.Job;
import sg.edu.iss.jinder.repo.JobRepository;

@Service
public class JobServiceImpl implements JobService 
{

	@Autowired
	JobRepository jobrepo;
	
	@Override
	public List<Job> listall(String keyword)
	{
		if(keyword !=null && keyword != "")
		{
			try {
				  String keywordParam = keyword.replace(" ", "+");
				  URL url = new URL("http://127.0.0.1:5000/search/?query=" + keywordParam); 
				  
				  HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				  
				  conn.setRequestMethod("GET");
			      conn.connect();
			      BufferedReader rd  = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			      StringBuilder sb = new StringBuilder();
			      String line = null;
		          while ((line = rd.readLine()) != null)
		          {
		              sb.append(line + '\n');
		          }
		          rd.close();
		          conn.disconnect();
		          
		          String[] arr = sb.toString().split(",");
		          List<Integer> sortedIds = new ArrayList<>();
		          for (int i = 0; i < arr.length-1; i++) {
		        	  sortedIds.add(Integer.parseInt(arr[i]));
		          }
		          List<Job> sortedJobs = new ArrayList<Job>();
		          for(Integer sortedId : sortedIds) {
		        	  sortedJobs.add(jobrepo.findById(sortedId).get());
		          }
		          return sortedJobs;
		          
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		else {
			return jobrepo.findAll();
		}
		
	}
	
	@Override
	public List<Job> listresult(String keyword, int id)
	{
		if(keyword !=null) {
			try {
				  String keywordParam = keyword.replace(" ", "+");
				  URL url = new URL("http://127.0.0.1:5000/searchwithresume/?query=" + keywordParam); 
				  
				  HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				  
				  conn.setRequestMethod("GET");
			      conn.connect();
			      BufferedReader rd  = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			      StringBuilder sb = new StringBuilder();
			      String line = null;
		          while ((line = rd.readLine()) != null)
		          {
		              sb.append(line + '\n');
		          }
		          rd.close();
		          conn.disconnect();
		          
		          String[] arr = sb.toString().split(",");
		          List<Integer> sortedIds = new ArrayList<>();
		          for (int i = 0; i < arr.length-1; i++) {
		        	  sortedIds.add(Integer.parseInt(arr[i]));
		          }
		          List<Job> sortedJobs = new ArrayList<Job>();
		          for(Integer sortedId : sortedIds) {
		        	  sortedJobs.add(jobrepo.findById(sortedId).get());
		          }
		          return sortedJobs;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		else {
			try {
				  URL url = new URL("http://127.0.0.1:5000/resume/?id=" + id); 
				  
				  HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				  
				  conn.setRequestMethod("GET");
			      conn.connect();
			      BufferedReader rd  = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			      StringBuilder sb = new StringBuilder();
			      String line = null;
		          while ((line = rd.readLine()) != null)
		          {
		              sb.append(line + '\n');
		          }
		          rd.close();
		          conn.disconnect();
		          
		          String[] arr = sb.toString().split(",");
		          List<Integer> sortedIds = new ArrayList<>();
		          for (int i = 0; i < arr.length-1; i++) {
		        	  sortedIds.add(Integer.parseInt(arr[i]));
		          }
		          List<Job> sortedJobs = new ArrayList<Job>();
		          for(Integer sortedId : sortedIds) {
		        	  sortedJobs.add(jobrepo.findById(sortedId).get());
		          }
		          return sortedJobs;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		
	}

	@Override
	public Page<Job> findPaginated(List<Job> jobs, Pageable pageable) 
	{
		
		int pageSize= pageable.getPageSize();
		int currentPage= pageable.getPageNumber();
		int startItem=currentPage*pageSize;
		List<Job> list;
		 if (jobs.size() < startItem)
		 {
	            list = Collections.emptyList();
	     }
		 else 
		{
			 int toIndex=Math.min(startItem +pageSize, jobs.size());
			 list=jobs.subList(startItem, toIndex);
		}
		 
		 Page<Job> jobPage= new PageImpl<Job>(list, PageRequest.of(currentPage, pageSize), jobs.size());
		
		 return jobPage;
	}

}
