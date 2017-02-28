package com.twitter.test;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import twitter4j.PagableResponseList;
import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.UserList;

public class Example {

	public static void main(String[] args) throws TwitterException, UnsupportedEncodingException {
		// The factory instance is re-useable and thread safe.
		Twitter twitter = TwitterFactory.getSingleton();

		// ConfigurationBuilder cb = new ConfigurationBuilder();
		// cb.setDebugEnabled(true).setOAuthConsumerKey("---")
		// .setOAuthConsumerSecret("---")
		// .setOAuthAccessToken("---")
		// .setOAuthAccessTokenSecret("---");

		// twitter.setOAuthConsumer("8h5qIHlOMxCIlP4McTRbAzyXJ",
		// "nonXOPboVMaBpxTXenKKRz8XaZn5CbpZb7B8U3r2rjQ3BqJhqS");

		// get followers data

		// ArrayList<User> listFollowers=new ArrayList();
		// String twitterScreenName = twitter.getScreenName();
		// //Mustafa A. Osman:55Bogy
		// System.out.println("SCREEN NAME:"+twitterScreenName);

		// long cursor = -1;
		// PagableResponseList<User> statuse=null;
		// do
		// {
		// statuse = twitter.getFollowersList(twitterScreenName, cursor);
		//
		// System.out.println(statuse.size()+":::::::::::::::::::::");
		//
		// for (User follower : statuse) {
		//
		//// System.out.println(follower.getName()+":"+follower.getScreenName());
		// listFollowers.add(follower);
		//
		// }
		// }while((cursor =statuse.getNextCursor())!=0);
		//
		//
		// System.out.println("All Followers:"+listFollowers.size());
		// for (User follower : listFollowers) {
		// System.out.println(follower.getName()+":"+follower.getScreenName());
		// }

		// Get home page data
		// ResponseList<Status> statuses=null;
		// String twitterScreenName = twitter.getScreenName();
		//
		// ArrayList<Status> homeScreenStatus=new ArrayList();
		//
		// for (int i = 1; i < 10; i++) {
		// Paging paging = new Paging(i);
		// statuses = twitter.getHomeTimeline(paging);
		// System.out.println("Showing home timeline.");
		// for (Status status : statuses) {
		//
		//// System.out.println("User ID:"+status.getUser().getId()+"\t
		// twwit_id:"+status.getId()+":"+status.getUser().getName() + ":" +
		//// new String(status.getText().getBytes(),"UTF-8"));
		// homeScreenStatus.add(status);
		// }
		// }
		// System.out.println("All twittes in home
		// page:"+homeScreenStatus.size());

		// get user lists
		String twitterScreenName = twitter.getScreenName();

		long cursor = -1;
		PagableResponseList<UserList> userLists;
		do {
			// membes = twitter.getUserListMembers(twitter.getId(),cursor);
			userLists = twitter.getUserListsOwnerships(twitter.getScreenName(), cursor);

			for (UserList oneUserList : userLists) {

				System.out.println(oneUserList.getId() + "\t" + oneUserList.getFullName() + "\t" + oneUserList.getName()
						+ "\t" + oneUserList.getAccessLevel());

				Set<String> members = new HashSet<String>();
				long cursorAccountInlIST = -1;
				PagableResponseList<User> accounts;
				do {
					accounts = twitter.getUserListMembers(oneUserList.getId(), cursorAccountInlIST);
					for (User user : accounts) {
                       getAllHomeTimeLineUser(twitter, user);
                       break;
					}
				} while ((cursor = userLists.getNextCursor()) != 0);

			}
		} while ((cursor = userLists.getNextCursor()) != 0);

		// System.out.println(members.size());

	}
	
	

	public void getAccounts()
	{
		
	}

	/**
	 * Get all users status
	 * @param twitter
	 * @param user
	 */
	public static void getAllHomeTimeLineUser(Twitter twitter, User user) {
		try {
			final Paging paging = new Paging();
			paging.count(200); // max statuses you can request for this call

			List<Status> statuses = twitter.getUserTimeline(user.getId(), paging);
			System.out.println("In List::" + user.getId() + "\t" + user.getName() + "\t" + user.getScreenName());

			for (Status status : statuses) {
				System.out.println("IN STATUS:" + status.getId() + "\t" + status.getText());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
