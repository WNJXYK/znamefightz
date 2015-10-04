/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.arthur.namefight.zhouzhi;

import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.ClipboardManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


public class Namefight extends Activity {

	Handler handler = new Handler();
	public EditText playname[]=new EditText[3];
    public EditText informationtext;
    Button startb,refreshb;
    ProgressBar hp[]=new ProgressBar[3];
    ProgressBar sp[]=new ProgressBar[3];
    ImageView bd[]=new ImageView[3];
    ImageView zd[]=new ImageView[3];
    TextView hptext[]=new TextView[3];
    public TextView bdn[]=new TextView[3];
    public TextView zdn[]=new TextView[3];
    Integer base[]=new Integer[3];//基础
    Integer step[]=new Integer[3];
    Integer maxhp[]=new Integer[3];
    Integer nowhp[]=new Integer[3];
    Integer def[]=new Integer[3];
    Integer att[]=new Integer[3];
    Integer spe[]=new Integer[3];
    Integer playbd[]=new Integer[3];
    Integer playzd[]=new Integer[26];
    String bdname[]=new String[10];
    String zdname[]=new String[11];
    Integer nowstep[]=new Integer[3];
    Integer nowspe[]=new Integer[3];
    Boolean startflag;
    Random random = new Random();
    Integer bds[]=new Integer[10];
    Integer zds[]=new Integer[12];

	public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.main);
    	playname[1]=(EditText) findViewById(R.id.name1);
    	playname[2]=(EditText) findViewById(R.id.name2);
    	bdn[1]=(TextView) findViewById(R.id.bdn1);
    	bdn[2]=(TextView) findViewById(R.id.bdn2);
    	zdn[1]=(TextView) findViewById(R.id.zdn1);
    	zdn[2]=(TextView) findViewById(R.id.zdn2);
    	hptext[1]=(TextView) findViewById(R.id.hpt1);
    	hptext[2]=(TextView) findViewById(R.id.hpt2);
    	informationtext=(EditText) findViewById(R.id.information);
    	startb=(Button) findViewById(R.id.start);
    	refreshb=(Button) findViewById(R.id.refresh);
    	hp[1]=(ProgressBar)findViewById(R.id.hp1);
    	hp[2]=(ProgressBar)findViewById(R.id.hp2);
    	sp[1]=(ProgressBar)findViewById(R.id.sp1);
    	sp[2]=(ProgressBar)findViewById(R.id.sp2);
    	bd[1]=(ImageView) findViewById(R.id.bd1);
    	bd[2]=(ImageView) findViewById(R.id.bd2);
    	zd[1]=(ImageView) findViewById(R.id.zd1);
    	zd[2]=(ImageView) findViewById(R.id.zd2);
    	startflag=false;
    	handler.post(gamerunnable);
    	bdname[1]="反击";
    	bdname[2]="蚀心";
    	bdname[3]="吸血";
    	bdname[4]="闪避";
    	bdname[5]="防御";
    	bdname[6]="肉盾";
    	bdname[7]="王道";
    	bdname[8]="衰弱";
    	bdname[9]="重击";
    	zdname[1]="普通攻击";
    	zdname[2]="暴怒";
    	zdname[3]="盛宴之咬";
    	zdname[4]="恢复";
    	zdname[5]="刷新";
    	zdname[6]="束缚击";
    	zdname[7]="牺牲";
    	zdname[8]="邪恶阴影";
    	zdname[9]="提升";
    	zdname[10]="激怒";
    	bds[1]=R.drawable.bd1;
    	bds[2]=R.drawable.bd2;
    	bds[3]=R.drawable.bd3;
    	bds[4]=R.drawable.bd4;
    	bds[5]=R.drawable.bd5;
    	bds[6]=R.drawable.bd6;
    	bds[7]=R.drawable.bd7;
    	bds[8]=R.drawable.bd8;
    	bds[9]=R.drawable.bd9;
    	zds[1]=R.drawable.zd1;
    	zds[2]=R.drawable.zd2;
    	zds[3]=R.drawable.zd3;
    	zds[4]=R.drawable.zd4;
    	zds[5]=R.drawable.zd5;
    	zds[6]=R.drawable.zd6;
    	zds[7]=R.drawable.zd7;
    	zds[8]=R.drawable.zd8;
    	zds[9]=R.drawable.zd9;
    	zds[10]=R.drawable.zd10;
    	Integer zdi;
    	for (zdi=1;zdi<=25;zdi++) playzd[zdi]=1;
    	playzd[1]=5;
    	playzd[3]=3;
    	playzd[5]=2;
    	playzd[7]=4;
    	playzd[11]=6;
    	playzd[13]=7;
    	playzd[17]=8;
    	playzd[19]=9;
    	playzd[23]=10;
    	playzd[2]=2;
    	playzd[6]=2;
    	playzd[10]=9;
    	playzd[12]=6;
    	playzd[18]=3;
    	
    	
    	OnClickListener startlisten=new OnClickListener(){
    		public void onClick(View v){
    			informationtext.setText("");
    			name_to_base(playname[1].getText().toString(),1);
    			name_to_base(playname[2].getText().toString(),2);
    			showhp();
    			startflag=true;
    		}
    	};
    	startb.setOnClickListener(startlisten);
    	OnClickListener refreshlisten=new OnClickListener(){
    		public void onClick(View v){
    			ClipboardManager cmb = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
    			cmb.setText(informationtext.getText().toString());
    			Toast toast = Toast.makeText(Namefight.this, "复制成功喽", Toast.LENGTH_SHORT); 
    			toast.show(); 

    		}
    	};
    	refreshb.setOnClickListener(refreshlisten);
    	
    	
    }

	public boolean onCreateOptionsMenu(Menu menu) {       
		// TODO Auto-generated method stub        
		menu.add(0, 1, 1, "技能帮助");       
		menu.add(0, 2, 2, "关于作者");        
		return super.onCreateOptionsMenu(menu);    }
	
	
	public void name_to_base(String name,Integer player){
		Integer namelength=name.length();
		Integer i,j,k;
		base[player]=0;
		for (i=0;i<namelength;i++){
			base[player]+=(int)name.toCharArray()[i];
		}
		step[player]=base[player] % 25+1;
		maxhp[player]=200+(base[player]%10)*50;
		def[player]=1+(base[player]%5);
		att[player]=70-(base[player]%10)*3-(base[player]%5)*4;
		spe[player]=1000+(base[player]%10)*300;
		nowspe[player]=0;
		playbd[player]=step[player]%9+1;
		if (playbd[player]==5) def[player]+=3;
		if (playbd[player]==6) maxhp[player]+=200;
		if (playbd[player]==7) {maxhp[player]=(int) ((int)maxhp[player]*1.1);att[player]=(int) ((int)att[player]*1.1);def[player]=(int) ((int)def[player]*1.1);}
		if (playbd[player]==8) {att[player]=(int) ((int)att[player]*1.20);}
		bdn[player].setText(bdname[playbd[player]]+"[被动]");
		nowstep[player]=step[player];
		nowhp[player]=maxhp[player];
		bd[player].setImageDrawable(getResources().getDrawable(bds[playbd[player]]));
		informationtext.append("【"+playname[player].getText().toString()+"】数据：\n    生命:"
		+String.valueOf(maxhp[player])+"点\n    攻击强度："
		+String.valueOf(att[player])+"点\n    防御等级："
		+String.valueOf(def[player])+"点\n    攻击速度："
		+String.valueOf(spe[player]/1000)+"秒/次\n");
	}
	public void showhp(){
		hp[1].setMax(maxhp[1]);
		hp[1].setProgress(nowhp[1]);
		hp[2].setMax(maxhp[2]);
		hp[2].setProgress(nowhp[2]);
		hptext[1].setText(String.valueOf(nowhp[1])+"/"+String.valueOf(maxhp[1]));
		hptext[2].setText(String.valueOf(nowhp[2])+"/"+String.valueOf(maxhp[2]));
	}
	public void showsp(){
		sp[1].setMax(spe[1]);
		sp[1].setProgress(nowspe[1]);
		sp[2].setMax(spe[2]);
		sp[2].setProgress(nowspe[2]);
	}
	public void damage(Integer p,Integer d){
		if (playbd[p]==4){//闪避
		Integer gl=(Math.abs(random.nextInt()))%100+1;
		if (gl<=20) {d=0;informationtext.append("【"+playname[p].getText().toString()+"】被动触发[闪避]，躲开了攻击。\n");
		}}
		
		if (playbd[3-p]==9){//重击
			Integer gl=(Math.abs(random.nextInt()))%100+1;
			if (gl<=70) {d+=20;informationtext.append("【"+playname[3-p].getText().toString()+"】被动触发[重击]，，对【"+playname[p].getText().toString()+"】造成了"+String.valueOf(20)+"点额外伤害。\n");
			}}
		
		nowhp[p]-=d;
		if (nowhp[p]<=0){ 
			nowhp[p]=0;
			startflag=false;
			informationtext.append("【"+playname[p].getText().toString()+"】死亡，【"+playname[3-p].getText().toString()+"】胜利！\n");
		}
		else{
		
		if (playbd[p]==1) {//反击
			Integer gl=(Math.abs(random.nextInt()))%100+1;
			
			if (gl<=15) {informationtext.append("【"+playname[p].getText().toString()+"】被动触发[反击]，对【"+playname[3-p].getText().toString()+"】造成了"+String.valueOf(60)+"点伤害。\n");damage(3-p,60);
			}}
		if (playbd[p]==2) {//腐蚀心
			
			informationtext.append("【"+playname[3-p].getText().toString()+"】受到了【"+playname[p].getText().toString()+"】[蚀心]的影响，受到了"+String.valueOf((int)(nowhp[3-p]*.05))+"点伤害。\n");
			nowhp[3-p]-=(int)(nowhp[3-p]*.05);
		}
		
		if (playbd[p]==3) {//吸血
			Integer gl=(Math.abs(random.nextInt()))%100+1;
			if (gl<=20) {informationtext.append("【"+playname[p].getText().toString()+"】被动触发[吸血]，对【"+playname[3-p].getText().toString()+"】造成了"+String.valueOf((int)(att[p]*(15-def[3-p])/15))+"点伤害并自己恢复生命值"+String.valueOf((int)(att[p]*(15-def[3-p])/15*0.9))+"点。\n");damage(3-p,(int)(att[p]*(15-def[3-p])/15));recover(p,(int)(att[p]*(15-def[3-p])/15*0.9));
			}}
		}
		
		showhp();
	}
	public void recover(Integer p,Integer d){
		nowhp[p]+=d;
		if (nowhp[p]>maxhp[p]){
			nowhp[p]=maxhp[p];
		}
		showhp();
	}
	public void attacks(Integer ap,Integer pp){
		String mystr;
		nowstep[ap]+=step[ap];
		nowstep[ap]=nowstep[ap]%25+1;
		/*普攻*/ if (playzd[nowstep[ap]]==1&&startflag==true) {
		informationtext.append("【"+playname[ap].getText().toString()+"】使用[普通攻击]，对【"+playname[pp].getText().toString()+"】造成了"+String.valueOf((int)att[ap]*(15-def[pp])/15)+"点伤害。\n");
		damage(pp,(int)(att[ap]*(15-def[pp])/15));	
		zdn[ap].setText("普通攻击");
		zd[ap].setImageDrawable(getResources().getDrawable(zds[1]));
		}
/*暴怒*/ if (playzd[nowstep[ap]]==2&&startflag==true)
	if (nowhp[ap]<=maxhp[ap]*0.7){
	informationtext.append("【"+playname[ap].getText().toString()+"】使用[暴怒]，对【"+playname[pp].getText().toString()+"】造成了"+String.valueOf((int)att[ap])+"点伤害。\n");
	damage(pp,(int)att[ap]);
	zdn[ap].setText("暴怒");	
	zd[ap].setImageDrawable(getResources().getDrawable(zds[2]));
}
else{
	informationtext.append("【"+playname[ap].getText().toString()+"】使用[普通攻击]，对【"+playname[pp].getText().toString()+"】造成了"+String.valueOf((int)att[ap]*(15-def[pp])/15)+"点伤害。\n");
	damage(pp,(int)(att[ap]*(15-def[pp])/15));	
	zdn[ap].setText("普通攻击");
	zd[ap].setImageDrawable(getResources().getDrawable(zds[1]));
}
/*盛宴之咬*/ if (playzd[nowstep[ap]]==3&&startflag==true)
	if (nowhp[ap]<=maxhp[ap]*0.4){
	informationtext.append("【"+playname[ap].getText().toString()+"】使用[盛宴之咬]，对【"+playname[pp].getText().toString()+"】造成了"+String.valueOf((int)(att[ap]*(15-def[pp])/15*2))+"点伤害并自己恢复生命值"+String.valueOf((int) ((int)att[ap]*(15-def[pp])/15*1.5*0.5))+"点。\n");
	damage(pp,(int) ((int)att[ap]*(15-def[pp])/15*2));
	recover(ap,(int) ((int)att[ap]*(15-def[pp])/15*2*0.5));	
	zdn[ap].setText("盛宴之咬");	
	zd[ap].setImageDrawable(getResources().getDrawable(zds[3]));
}
else{
	informationtext.append("【"+playname[ap].getText().toString()+"】使用[普通攻击]，对【"+playname[pp].getText().toString()+"】造成了"+String.valueOf((int)att[ap]*(15-def[pp])/15)+"点伤害。\n");
	damage(pp,(int)(att[ap]*(15-def[pp])/15));	
	zdn[ap].setText("普通攻击");
	zd[ap].setImageDrawable(getResources().getDrawable(zds[1]));
}
/*恢复*/ if (playzd[nowstep[ap]]==4&&startflag==true)
	if (nowhp[ap]<=maxhp[ap]*0.3){
	informationtext.append("【"+playname[ap].getText().toString()+"】使用[恢复]，恢复了自己生命值"+String.valueOf(100)+"点。\n");
	recover(ap,100);
	zdn[ap].setText("恢复");	
	zd[ap].setImageDrawable(getResources().getDrawable(zds[4]));
}else{
	informationtext.append("【"+playname[ap].getText().toString()+"】使用[普通攻击]，对【"+playname[pp].getText().toString()+"】造成了"+String.valueOf((int)att[ap]*(15-def[pp])/15)+"点伤害。\n");
	damage(pp,(int)(att[ap]*(15-def[pp])/15));	
	zdn[ap].setText("普通攻击");
	zd[ap].setImageDrawable(getResources().getDrawable(zds[1]));
}
/*刷新*/ if (playzd[nowstep[ap]]==5&&startflag==true) {
	informationtext.append("【"+playname[ap].getText().toString()+"】使用[刷新]，立刻对【"+playname[pp].getText().toString()+"】发动攻击，造成了"+String.valueOf((int)att[ap]*(15-def[pp])/15)+"点伤害。并马上进行下一次攻击。\n");
	damage(pp,(int)(att[ap]*(15-def[pp])/15));	
	zdn[ap].setText("刷新");
	zd[ap].setImageDrawable(getResources().getDrawable(zds[5]));
    attacks(ap,pp);
}
/*束缚击*/ if (playzd[nowstep[ap]]==6&&startflag==true) {
	informationtext.append("【"+playname[ap].getText().toString()+"】使用[束缚击]，对【"+playname[pp].getText().toString()+"】造成了"+String.valueOf((int)att[ap])+"点伤害并打断对方攻击准备状态！\n");
	nowspe[pp]=0;
	damage(pp,(int)att[ap]);
	zdn[ap].setText("束缚击");
	zd[ap].setImageDrawable(getResources().getDrawable(zds[6]));
	showsp();
}
/*牺牲*/ if (playzd[nowstep[ap]]==7 &&startflag==true) 
	if (nowhp[ap]>=maxhp[ap]*0.8){
	informationtext.append("【"+playname[ap].getText().toString()+"】使用[牺牲]，对【"+playname[pp].getText().toString()+"】造成了当前生命值50%的伤害，对自己造成当前生命值40%伤害。\n");
	damage(pp,(int) (nowhp[pp]*0.5));
	damage(ap,(int) (nowhp[ap]*0.4));
	zdn[ap].setText("牺牲");	zd[ap].setImageDrawable(getResources().getDrawable(zds[7]));
	}
	else{
	informationtext.append("【"+playname[ap].getText().toString()+"】使用[普通攻击]，对【"+playname[pp].getText().toString()+"】造成了"+String.valueOf((int)att[ap]*(15-def[pp])/15)+"点伤害。\n");
	damage(pp,(int)(att[ap]*(15-def[pp])/15));	
	zdn[ap].setText("普通攻击");
	zd[ap].setImageDrawable(getResources().getDrawable(zds[1]));
}
/*邪恶阴影*/ if (playzd[nowstep[ap]]==8&&startflag==true) {
	Integer gl=random.nextInt()%10+1;
	if (gl<=6){
		informationtext.append("【"+playname[ap].getText().toString()+"】使用[邪恶阴影]，对【"+playname[pp].getText().toString()+"】造成了"+String.valueOf(70)+"点伤害\n");
		damage(pp,70);
		zdn[ap].setText("邪恶阴影");	
		zd[ap].setImageDrawable(getResources().getDrawable(zds[8]));
	}
	else{
		informationtext.append("【"+playname[ap].getText().toString()+"】使用[邪恶阴影]，可是压空了~\n");
		zdn[ap].setText("邪恶阴影");
		zd[ap].setImageDrawable(getResources().getDrawable(zds[8]));
	}
	}
	/*提升*/ if (playzd[nowstep[ap]]==9&&startflag==true) {
		att[ap]+=7;
		informationtext.append("【"+playname[ap].getText().toString()+"】使用[提升]，提升了自己的攻击力。\n");
		zdn[ap].setText("提升");
		zd[ap].setImageDrawable(getResources().getDrawable(zds[9]));
	}
	/*激怒*/ if (playzd[nowstep[ap]]==10&&startflag==true) {
		informationtext.append("【"+playname[ap].getText().toString()+"】使用[激怒]，附加大幅攻击力。对【"+playname[pp].getText().toString()+"】,造成了"+String.valueOf((int)((att[ap])*(15-def[pp])/15))+"+"+String.valueOf((int)((maxhp[ap]*.06)*(15-def[pp])/15))+"点。\n");
		damage(pp,(int)((att[ap]+maxhp[ap]*.06)*(15-def[pp])/15));	
		zdn[ap].setText("激怒");
		zd[ap].setImageDrawable(getResources().getDrawable(zds[10]));
	}


	}
	public void attackprogress(){
		Integer i;
		for (i=1;i<=2;i++){
			if (startflag==true) nowspe[i]+=100;
		}
		for (i=1;i<=2;i++){
			if (nowspe[i]>=spe[i]&&startflag==true) {
				attacks(i,3-i);
				nowspe[i]=0;
			}
		}
		showsp();
	}
	
	
	Runnable gamerunnable=new Runnable(){
		public void run(){
			if (startflag==true) attackprogress();
			handler.postDelayed(gamerunnable,100);
		}
		};
		public boolean onOptionsItemSelected(MenuItem item) {  
			// TODO Auto-generated method stub       
			if(item.getItemId() == 1){          
				LayoutInflater inflater = getLayoutInflater();
				View layout = inflater.inflate(R.layout.help,(ViewGroup) findViewById(R.id.scrollView));
				new AlertDialog.Builder(this)
				.setTitle("技能帮助")
				.setIcon(android.R.drawable.ic_dialog_info)
				.setView(layout)
				.setPositiveButton("确定", null)
				.setNegativeButton("取消", null)
				.show();}
			if(item.getItemId() == 2){          
				LayoutInflater inflater = getLayoutInflater();
				View layout = inflater.inflate(R.layout.about,(ViewGroup) findViewById(R.id.layoutline));
				new AlertDialog.Builder(this)
				.setTitle("关于作者")
				.setIcon(android.R.drawable.ic_dialog_info)
				.setView(layout)
				.setPositiveButton("确定", null)
				.setNegativeButton("取消", null)
				.show();}

			return true;    }
	
	
		
	}

