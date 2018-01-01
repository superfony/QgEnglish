package qge.cn.com.qgenglish.app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;

import java.util.HashMap;
import java.util.Map;


@SuppressLint("NewApi")
public class Common {
	public static Map<String,Fragment> map=new HashMap<String,Fragment>();
	public static void replaceRightFragment(Activity activity, Fragment fragment, boolean popBackStack, int layoutFragment, String key) {
		if(popBackStack)
			activity.getFragmentManager().popBackStack();
		FragmentTransaction fragmentTransaction = activity.getFragmentManager()
				.beginTransaction();
		fragmentTransaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out, android.R.animator.fade_in, android.R.animator.fade_out);
		
		if("groupbookft".equals(key)){
			if(map.get(key)!=null){
				fragmentTransaction.show(map.get(key));
			}else{
				fragmentTransaction.add(layoutFragment, fragment);
				map.put(key, fragment);
			}
		}else if("wordinfo".equals(key)){
			for(int i=0;i<activity.getFragmentManager().getBackStackEntryCount();i++){
 				activity.getFragmentManager().popBackStack();
 			}
			fragmentTransaction.replace(layoutFragment, fragment);
			
 		}else if("departftchild".equals(key)){
 			fragmentTransaction.replace(layoutFragment, fragment);
			//fragmentTransaction.addToBackStack(null); // 是否保留返回操作
 		}
		fragmentTransaction.commit();
	}

	public static void clear(Activity activity){
		map.clear();
		FragmentTransaction fragmentTransaction = activity.getFragmentManager()
				.beginTransaction();
		for(int i=0;i<activity.getFragmentManager().getBackStackEntryCount();i++){
			activity.getFragmentManager().popBackStack();
		}
	}
	
	public static void replaceRightFragment(Activity activity, Fragment fragment, boolean popBackStack, int layoutFragment) {
		if(popBackStack)
			activity.getFragmentManager().popBackStack();
		FragmentTransaction fragmentTransaction = activity.getFragmentManager()
				.beginTransaction();
	//	fragmentTransaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out, android.R.animator.fade_in, android.R.animator.fade_out);
		fragmentTransaction.replace(layoutFragment, fragment);
//		fragmentTransaction.addToBackStack(null); // 是否保留返回操作
		fragmentTransaction.commit();
	}

//
//	public static void replaceRightFragment(FragmentActivity activity,Fragment fragment,boolean popBackStack,int layoutFragment) {
//		if(popBackStack)
//			activity.getFragmentManager().popBackStack();
//
//		FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager()
//				.beginTransaction();
//
//		//fragmentTransaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out, android.R.animator.fade_in, android.R.animator.fade_out);
//		fragmentTransaction.replace(layoutFragment, fragment);
//		fragmentTransaction.addToBackStack(null);
//		fragmentTransaction.commit();
//	}

}
