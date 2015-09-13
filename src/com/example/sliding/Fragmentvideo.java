package com.example.sliding;

import com.example.sliding.R;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;
 
public class Fragmentvideo extends Fragment {
	private static final String TAG = "VideoPlayer";

private VideoView videoView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_video, container, false);
        videoView = (VideoView) rootView.findViewById(R.id.videoView1);
        return rootView;
    }
	@Override
public void onActivityCreated(Bundle savedInstanceState) {
super.onActivityCreated(savedInstanceState);
videoView.setMediaController(new MediaController(getActivity()));
}

public void playVideo() {
Uri uri = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.raw.test_vid);
Log.d(TAG, "Uri is: " + uri);
setVideoLocation(uri);
if (!videoView.isPlaying()) {
videoView.start();
}
}

private void setVideoLocation(Uri uri) {
try {
videoView.setVideoURI(uri);
} catch (Exception e) {
Log.e(TAG, "VideoPlayer uri was invalid", e);
Toast.makeText(getActivity(), "Not found", Toast.LENGTH_SHORT).show();
}
}

public void pauseVideo() {
videoView.pause();
}
}