package test.Geoserver;

import it.geosolutions.geoserver.rest.GeoServerRESTPublisher;
import it.geosolutions.geoserver.rest.encoder.GSLayerEncoder;
import it.geosolutions.geoserver.rest.encoder.coverage.GSImageMosaicEncoder;

import java.io.File;
import java.io.FileNotFoundException;

public class Geoserver_manager {

	public static void main(String[] args) throws FileNotFoundException, IllegalArgumentException {
		GeoServerRESTPublisher publisher = new GeoServerRESTPublisher("http://localhost:8080/geoserver", "admin", "geoserver");
		
		
		boolean created = publisher.createWorkspace("myWorkspace"); //creating a workspace
		File zipFile = new File("G:\\PS 1\\SATIMAGES\\shapefile.zip");//storing location of zip file containing the shapefile in a variable
        boolean published = publisher.publishShp("myWorkspace", "myStore", "cities", zipFile, "EPSG:4326", "default_point");//publishing the shapefile
		
		boolean pub = publisher.publishGeoTIFF("test", "myTIFF", new File("G:\\PS 1\\SATIMAGES\\FCC_clip_Ahm.tif")); //single tiff image

		
        boolean ok = publisher.publishDBLayer("myWorkspace", "pg_kids", "easia_gaul_0_aggr", "EPSG:4326", "default_polygon");//publishing from a database table
				
        //***************************************
        //multiple images as mosaic
        // layer encoder
		final GSLayerEncoder layerEnc = new GSLayerEncoder();
        String style="raster";
        layerEnc.setDefaultStyle(style);

        // coverage encoder
        final GSImageMosaicEncoder coverageEnc=new GSImageMosaicEncoder();
        coverageEnc.setName("DEMImageName");
        coverageEnc.setTitle("DEMAnotherTitle_new");
        coverageEnc.setMaxAllowedTiles(500); 

        // ... many other options are supported

        // create a new ImageMosaic layer...
        published = publisher.publishExternalMosaic("test", "myDEM", new File("G:\\PS 1\\SATIMAGES\\DEM"), coverageEnc, layerEnc);

	}

}
