package com.example.sliding;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.sliding.GeoResponse;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.slidingmenu.adapter.NavDrawerListAdapter;
import com.slidingmenu.model.NavDrawerItem;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    TextView txtJson;
	HttpClient client;
	JSONObject json, json1;
	String data;
	SQLiteDatabase db;
	double lon;
	double lat;
	float mag, depth;
	Long time;
	String alert, place;
	int felt, o,t;
	final static String URL = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/";
	ContentValues val = new ContentValues();

    // nav drawer title
    private CharSequence mDrawerTitle;
 
    // used to store app title
    private CharSequence mTitle;
     float latitude;
    float longitude;
    
    // slide menu items
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;
    MapFragment freg;
    GoogleMap gr;
    int count,count1,count2;
    int j,i=0;
    String k;
    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        client = new DefaultHttpClient();
		
       db=openOrCreateDatabase("test", SQLiteDatabase.CREATE_IF_NECESSARY, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS disaster_day(latitude real,longitude real,depth real,mag real,time real,place text)");
        db.execSQL("CREATE TABLE IF NOT EXISTS disaster_week(latitude real,longitude real,depth real,mag real,time real,place text)");
        db.execSQL("CREATE TABLE IF NOT EXISTS disaster_all(latitude real,longitude real,depth real,mag real,time real,place text)");
        new Read().execute("features");
        gr=((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        mTitle = mDrawerTitle = getTitle();
        
        // load slide menu items
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
 
        // nav drawer icons from resources
        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);
 
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.list_slidermenu);
 
        navDrawerItems = new ArrayList<NavDrawerItem>();
 
        // adding nav drawer items to array
        // Home
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
        // Find People
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
        // Photos
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
        // Communities, Will add a counter here
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));
        // Pages
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));
        // What's hot, We  will add a counter here
        
         
 
        // Recycle the typed array
        navMenuIcons.recycle();
 
        // setting the nav drawer list adapter
        adapter = new NavDrawerListAdapter(getApplicationContext(),
                navDrawerItems);
        mDrawerList.setAdapter(adapter);
 
        // enabling action bar app icon and behaving it as toggle button
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
 
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, //nav menu toggle icon
                R.string.app_name, // nav drawer open - description for accessibility
                R.string.app_name // nav drawer close - description for accessibility
        ){
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                // calling onPrepareOptionsMenu() to show action bar icons
                invalidateOptionsMenu();
            }
 
            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                // calling onPrepareOptionsMenu() to hide action bar icons
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
 
        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());
       
    }
    private class SlideMenuClickListener implements
    ListView.OnItemClickListener {
    	@Override
    	public void onItemClick(AdapterView<?> parent, View view, int position,
        long id) {
    // display view for selected nav drawer item
    		displayView(position);
    	}
    }
    private void displayView(int position) {
        // update the main content by replacing fragments
    	try{
        switch (position) {
        case 0:
        	gr.clear();
        	Cursor p=db.rawQuery("SELECT count(*) FROM disaster_day", null);
        	p.moveToFirst();
        	while(!p.isAfterLast())
        	{
        	count=p.getInt(0);
        	p.moveToNext();
        	}
        	p.close();
        	MarkerOptions mar[]=new MarkerOptions[count];
        	Cursor qw=db.rawQuery("SELECT * FROM disaster_day",null);
          qw.moveToFirst();
            while(!qw.isAfterLast())
            {
            	latitude=qw.getFloat(0);
            	longitude=qw.getFloat(1);
            	depth=qw.getLong(2);
            	mag=qw.getLong(3);
            	time=qw.getLong(4);
            	place=qw.getString(5);
            	mar[i] = new MarkerOptions().position(new LatLng(latitude, longitude)).title("mag-"+mag+"-place-"+place);
            	gr.addMarker(mar[i]);
            i++;
            qw.moveToNext();
            }
            qw.close();
            i=0;
        	break;        
        case 1:
        	gr.clear();
        	Cursor p1=db.rawQuery("SELECT count(*) FROM disaster_week", null);
        	p1.moveToFirst();
        	while(!p1.isAfterLast())
        	{
        	count1=p1.getInt(0);
        	p1.moveToNext();
        	}
        	p1.close();
        	MarkerOptions mar1[]=new MarkerOptions[count1];
        	Cursor qw1=db.rawQuery("SELECT * FROM disaster_week",null);
            
          qw1.moveToFirst();
            while(!qw1.isAfterLast())
            {
            	latitude=qw1.getFloat(0);
            	longitude=qw1.getFloat(1);
            	depth=qw1.getLong(2);
            	mag=qw1.getLong(3);
            	time=qw1.getLong(4);
            	place=qw1.getString(5);
            	mar1[j] = new MarkerOptions().position(new LatLng(latitude, longitude)).title("mag-"+mag+"-place-"+place);
            	gr.addMarker(mar1[j]);
            j++;
            qw1.moveToNext();
            }
            qw1.close();
            j=0;
        	break;
        case 2:
        	gr.clear();
        	Cursor p2=db.rawQuery("SELECT count(*) FROM disaster_all", null);
        	p2.moveToFirst();
        	while(!p2.isAfterLast())
        	{
        	count2=p2.getInt(0);
        	p2.moveToNext();
        	}
        	p2.close();
        	MarkerOptions mar2[]=new MarkerOptions[count2];
        	Cursor qw2=db.rawQuery("SELECT * FROM disaster_all",null);
                      qw2.moveToFirst();
            while(!qw2.isAfterLast())
            {
            	latitude=qw2.getFloat(0);
            	longitude=qw2.getFloat(1);
            	depth=qw2.getLong(2);
            	mag=qw2.getLong(3);
            	time=qw2.getLong(4);
            	place=qw2.getString(5);
            	mar2[t] = new MarkerOptions().position(new LatLng(latitude, longitude)).title("mag-"+mag+"-place-"+place);
            	gr.addMarker(mar2[t]);
            t++;
            qw2.moveToNext();
            }
            qw2.close();
            t=0;
        	break;
        case 3:
             Intent i=new Intent(getApplicationContext(),Video1.class);
        	startActivity(i);
            break;
        case 4:
        	Intent i1=new Intent(getApplicationContext(),About.class);
        	startActivity(i1);
            break;
        default:
            break;
        }
    	}
        catch(Exception e){
        	Toast.makeText(getApplicationContext(), ""+e, Toast.LENGTH_LONG).show();
        }
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
        case R.id.action_search:
            // search action
        	gr.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            return true;
        case R.id.action_satellite:
            // location found
        	gr.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            return true;
        case R.id.action_refresh:
            // refresh
        	gr.clear();
            return true;
        case R.id.action_help:
            // help action
        	Toast.makeText(getApplicationContext(), "REAL TIME ALERTS.OPEN NAVIGATION DRAWER", Toast.LENGTH_LONG).show();
            return true;
        case R.id.action_normal:
            // check for updates action
        	gr.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }      
    	}
    

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }
    public boolean onPrepareOptionsMenu(Menu menu) {
        // if nav drawer is opened, hide the action items
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }
 
    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle("TREMBLER");
    }
 
    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */
 
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }
 
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
	private GeoResponse earthquake(String magnitude)
			throws ClientProtocolException, IOException, JSONException {

		GeoResponse geoResp = new GeoResponse();
		List<JSONObject> geocoordList = new ArrayList<JSONObject>();
		List<JSONObject> propList = new ArrayList<JSONObject>();
		StringBuilder url = new StringBuilder(URL);
		url.append(magnitude);
		HttpGet get = new HttpGet(url.toString());
		HttpResponse response = client.execute(get);
		int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode == 200) {
			HttpEntity ent = response.getEntity();
			String data = EntityUtils.toString(ent);

			JSONObject main_obj = new JSONObject(data);
			JSONArray list = main_obj.getJSONArray("features");
			for (int loopVar = 0; loopVar < list.length(); loopVar++) {
				JSONObject latest = list.getJSONObject(loopVar);
				JSONObject geometry_obj = latest.getJSONObject("geometry");
				JSONObject propObj = latest.getJSONObject("properties");
				propList.add(propObj);
				geocoordList.add(geometry_obj);
			}

			geoResp.setGeoCoord(geocoordList);
			geoResp.setProperties(propList);
			return geoResp;
		}
		return null;
	}

	public class Read extends AsyncTask<String, Integer, String> {

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			String re = "";
			String me = "";
			try {

				// StringBuilder stringBuilder = new StringBuilder();
				GeoResponse geoResp = earthquake("4.5_day.geojson");
				GeoResponse geoResp1 = earthquake("4.5_week.geojson");
				GeoResponse geoResp2 = earthquake("all_day.geojson");
				
				me = dbinsert(me, geoResp,"disaster_day");
				me = dbinsert(me, geoResp1,"disaster_week");
				me = dbinsert(me, geoResp2,"disaster_all");
				re = me;
			} catch (Exception e) {
				

			} finally {
			}
			return re;
		}

		private String dbinsert(String me, GeoResponse geoResp,String table)
				throws JSONException {
			Iterator<JSONObject> it = geoResp.getGeoCoord().iterator();
			Iterator<JSONObject> itProp = geoResp.getProperties()
					.iterator();
			while (it.hasNext() && itProp.hasNext()) {
				o++;
				json = it.next();
				json1 = itProp.next();
				val.put("mag", Float.parseFloat(json1.getString("mag")));
				val.put("place", json1.getString("place"));
				val.put("time", Long.parseLong(json1.getString("time")));
				JSONArray coordinates_array = json
						.getJSONArray("coordinates");
				for (int i = 0; i < coordinates_array.length(); i++) {
					// stringBuilder.append(coordinates_array.getString(i)+",");
					me = me + coordinates_array.getString(i) + ",||||,";
					if ((i + 1) % 3 == 1) {
						lon = Double.parseDouble(coordinates_array
								.getString(i));
						val.put("longitude", lon);
					}
					if ((i + 1) % 3 == 2) {
						lat = Double.parseDouble(coordinates_array
								.getString(i));
						val.put("latitude", lat);
					}
					if ((i + 1) % 3 == 0) {
						depth = Float.parseFloat(coordinates_array
								.getString(i));
						val.put("depth", depth);
					}

				}
				db.insert(table, null, val);
			}
			return me;
		}
	}
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
	}
}