package sg.edu.iss.jinder.service;

import java.io.BufferedReader; 
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
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
import sg.edu.iss.jinder.model.Job_Clicked;
import sg.edu.iss.jinder.model.JobSeeker;
import sg.edu.iss.jinder.repo.JobRepository;
import sg.edu.iss.jinder.repo.Job_ClickedRepository;
import sg.edu.iss.jinder.repo.UserPreferenceRepository;

@Service
public class JobServiceImpl implements JobService {
	
	@Autowired
	JobRepository jrepo;
	
	@Autowired
	Job_ClickedRepository job_clickedrepo;
	
	@Autowired
	UserPreferenceRepository uPrefrepo;
	
	@Override
	public List<Job> listAll(String keyword) {
		if(keyword !=null && keyword != "") {
			try {
				  String keywordParam = keyword.replace(" ", "+");
				  URL url = new URL("http://127.0.0.1:5000/search/?query=" + keywordParam); 
				  
				  HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				  
				  conn.setRequestMethod("GET");
			      conn.connect();
			      BufferedReader rd  = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			      StringBuilder sb = new StringBuilder();
			      String line = null;
		          while ((line = rd.readLine()) != null) {
		              sb.append(line + '\n');
		          }
		          rd.close();
		          conn.disconnect();
		          
		          String[] arr = sb.toString().split(",");
		          System.out.print(arr);
		          List<Integer> sortedIds = new ArrayList<>();
		          for (int i = 0; i < arr.length-1; i++) {
		        	  sortedIds.add(Integer.parseInt(arr[i]));
		          }
		          List<Job> sortedJobs = new ArrayList<Job>();
		          for (Integer sortedId : sortedIds) {
		        	  sortedJobs.add(jrepo.findById(sortedId).get());
		          }
		          return sortedJobs;
		          
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		else {
			return jrepo.findAll();
		}
	}
	
	@Override
	public List<Job> listResult(String keyword, int id) {
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
		          while ((line = rd.readLine()) != null) {
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
		        	  sortedJobs.add(jrepo.findById(sortedId).get());
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
		          while ((line = rd.readLine()) != null) {
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
		        	  sortedJobs.add(jrepo.findById(sortedId).get());
		          }
		          return sortedJobs;
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return null;
		}
	}

	@Override
	public Page<Job> findPaginated(List<Job> jobs, Pageable pageable) {
		int pageSize= pageable.getPageSize();
		int currentPage= pageable.getPageNumber();
		int startItem=currentPage*pageSize;
		List<Job> list;
		 if (jobs.size() < startItem) {
			 list = Collections.emptyList();
	     }
		 else {
			 int toIndex=Math.min(startItem +pageSize, jobs.size());
			 list=jobs.subList(startItem, toIndex);
		}
		 Page<Job> jobPage= new PageImpl<Job>(list, PageRequest.of(currentPage, pageSize), jobs.size());
		
		 return jobPage;
	}

	@Override
	public Job findJobById(Integer id) {
		
		return jrepo.findById(id).get();
	}

	@Override
	public List<Job_Clicked> findJob_ClickedsbyUserId(int id) {
		if(job_clickedrepo.findJob_ClickedsbyUserId(id)!=null) {
			
			return job_clickedrepo.findJob_ClickedsbyUserId(id);
		}
		else {
			
			return null;
		}
	}

	@Override
	public List<Job_Clicked> findJob_ClickedsbyJobId(int id) {
		if(job_clickedrepo.findJob_ClickedsbyJobId(id) != null) {
			
			return job_clickedrepo.findJob_ClickedsbyUserId(id);
		}
		else {
			
			return null;
		}
	}
		
	@Override
	public List<Job> listRecommendedJobsByClickHistory(JobSeeker user) {
		List<Job_Clicked> job_ClickedsbyUserClickeds = findJob_ClickedsbyUserId(user.getId());
		try {
			  String jobid_1 = String.valueOf(job_ClickedsbyUserClickeds.get(job_ClickedsbyUserClickeds.size()-1).getJob().getId()).replace(" ", "+");
			  URL url = new URL("http://127.0.0.1:5000/similarjobs/?jobid=" + jobid_1); 
			  
			  HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			  
			  conn.setRequestMethod("GET");
		      conn.connect();
		      BufferedReader rd  = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		      StringBuilder sb = new StringBuilder();
		      String line = null;
	          while ((line = rd.readLine()) != null) {
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
	        	  sortedJobs.add(jrepo.findById(sortedId).get());
	          }
	          
	          return sortedJobs;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	public List<Job> listRecommendedJobsBySurvey(JobSeeker user) {
		try {
			String userId = String.valueOf(user.getId());
			URL url = new URL("http://127.0.0.1:5000/prefsurvey/?id=" + userId); 
			
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			  
			conn.setRequestMethod("GET");
		    conn.connect();
		    BufferedReader rd  = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		    StringBuilder sb = new StringBuilder();
		    String line = null;
	        while ((line = rd.readLine()) != null) {
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
	        	sortedJobs.add(jrepo.findById(sortedId).get());
	        }
	          
	          return sortedJobs;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
}
