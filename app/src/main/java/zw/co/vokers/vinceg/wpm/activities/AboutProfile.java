package zw.co.vokers.vinceg.wpm.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import zw.co.vokers.vinceg.wpm.R;
import zw.co.vokers.vinceg.wpm.utils.FancyAboutPage;

/**
 *
 * Created by VinceGee on 29/1/2019
 */

public class AboutProfile extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aboutprofile);
        setTitle("About Page");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        FancyAboutPage fancyAboutPage=findViewById(R.id.fancyaboutpage);
        //fancyAboutPage.setCoverTintColor(Color.BLUE); //Optional
        fancyAboutPage.setCover(R.drawable.volvo);
        fancyAboutPage.setName("Shashank Singhal");
        fancyAboutPage.setDescription("Google Certified Associate Android Developer | Android App, Game, Web and Software Developer.");
        fancyAboutPage.setAppIcon(R.drawable.whelson);
        fancyAboutPage.setAppName("Cake Pop Icon Pack");
        fancyAboutPage.setVersionNameAsAppSubTitle("1.2.3");
        fancyAboutPage.setAppDescription("Cake Pop Icon Pack is an icon pack which follows Google's Material Design language.\n\n" +
                "This icon pack uses the material design color palette given by google. Every icon is handcrafted with attention to the smallest details!\n\n"+
                "A fresh new take on Material Design iconography. Cake Pop offers unique, creative and vibrant icons. Spice up your phones home-screen by giving it a fresh and unique look with Polycon.");
        fancyAboutPage.addEmailLink("shashanksinghal02@gmail.com");
        fancyAboutPage.addFacebookLink("https://www.facebook.com/shashanksinghal02");
        fancyAboutPage.addTwitterLink("https://twitter.com/shashank020597");
        fancyAboutPage.addLinkedinLink("https://www.linkedin.com/in/shashank-singhal-a87729b5/");
        fancyAboutPage.addGitHubLink("https://github.com/Shashank02051997");

    }
}
