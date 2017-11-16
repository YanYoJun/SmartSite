package com.isoftstone.smartsite.model.video;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import com.isoftstone.smartsite.R;
import com.isoftstone.smartsite.model.system.ui.PermissionsActivity;
import com.isoftstone.smartsite.model.system.ui.PermissionsChecker;
import com.isoftstone.smartsite.model.video.Adapter.PhotoGridAdapter;
import com.isoftstone.smartsite.model.video.bean.PhotoInfo;
import com.isoftstone.smartsite.model.video.bean.PhotoList;
import com.isoftstone.smartsite.model.video.utils.CheckImageLoaderConfiguration;
import com.isoftstone.smartsite.model.video.utils.UniversalImageLoadTool;
import com.isoftstone.smartsite.utils.ToastUtils;
import java.util.ArrayList;
import java.util.List;

public class SnapPicturesActivity extends Activity {
	
	private GridView gridView;
	private PhotoGridAdapter photoAdapter;
	private List<PhotoInfo> list;

	@Override
	public void onStart() {
		super.onStart();
		CheckImageLoaderConfiguration.checkImageLoaderConfiguration(this);
	}

	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_snap_pictures);

		gridView = (GridView) findViewById(R.id.gv_photos);

		if(null == bundle) {
			bundle = this.getIntent().getExtras();
		}

		PhotoList photoGrid = (PhotoList) bundle.getSerializable("list");
		list = new ArrayList<PhotoInfo>();
		list.addAll(photoGrid.getList());
		photoAdapter=new PhotoGridAdapter(this,list);

		gridView.setAdapter(photoAdapter);
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				//打开指定的一张照片
				String pictureFilepath = list.get(position).getPath_file();
				Intent intent = new Intent();
				intent.setAction(android.content.Intent.ACTION_VIEW);
				intent.setDataAndType(Uri.parse(pictureFilepath), "image/*");
				startActivity(intent);

				//打开系统相册浏览照片  
				/**Intent intent2 = new Intent(Intent.ACTION_VIEW, Uri.parse("content://media/internal/images/media"));
				startActivity(intent2);*/
			}
		});
		gridView.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				if(scrollState==0){
					UniversalImageLoadTool.resume();
				}else{
					UniversalImageLoadTool.pause();
				}
			}
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
								 int visibleItemCount, int totalItemCount) {
			}
		});
	}
}
