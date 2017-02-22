/**
 * 
 */
package tuwien.dbai.wpps.core.junit.test;


/**
 * @author Ruslan (ruslanrf@gmail.com)
 * @created Nov 27, 2011 12:22:50 AM
 */
public class TestInstanceAdaptersModule {

//	/**
//	 * Test method for {@link tuwien.dbai.wpps.core.module.InstanceAdaptersModule#configure()}.
//	 */
//	@Test
//	public void testConfigure() {
//System.out.println( "=== testConfigure() ===");
//		
//		Stopwatch stopwatch = new Stopwatch().start();
//		   
//		Injector inj = Guice.createInjector(new InstanceAdaptersModule(), new OntologyModule(), new WPPSConfigModule());
//		
//		System.out.println("time elapsed:"+stopwatch);
//		stopwatch.reset();stopwatch.start();
//		
//		WPOntModel wpModel = inj.getInstance(WPOntModel.class);
//		
//		System.out.println("time elapsed:"+stopwatch);
//		stopwatch.reset();stopwatch.start();
//		
//		Model model = wpModel.getOntAdapter(EWPOntModel.BLOCK_GEOM_OBJECT_STRUCT_MODEL).getRdfModel();
//		String ns = wpModel.getOntAdapter(EWPOntModel.BLOCK_GEOM_OBJECT_STRUCT_MODEL).getNameSpace();
//		
//		Resource wpRes = model.createResource(ns+"webpage");
//		model.add(wpRes, RDF.type, BlockGeomObjectOnt.Page);
//		Resource windowRes1 = model.createResource(ns+"window1");
//		model.add(windowRes1, RDF.type, BlockGeomObjectOnt.Window);
//		Resource windowRes2 = model.createResource(ns+"window2");
//		model.add(windowRes2, RDF.type, BlockGeomObjectOnt.Window);
//		Resource windowRes11 = model.createResource(ns+"window11");
//		model.add(windowRes11, RDF.type, BlockGeomObjectOnt.Window);
//		Resource windowRes12 = model.createResource(ns+"window12");
//		model.add(windowRes12, RDF.type, BlockGeomObjectOnt.Window);
//		Resource windowRes21 = model.createResource(ns+"window21");
//		model.add(windowRes21, RDF.type, BlockGeomObjectOnt.Window);
//		Resource vpRes = model.createResource(ns+"viewport");
//		model.add(vpRes, RDF.type, BlockGeomObjectOnt.ViewPort);
//		Resource boxRes1 = model.createResource(ns+"box1");
//		model.add(boxRes1, RDF.type, BlockGeomObjectOnt.Box);
//		Resource iblockRes1 = model.createResource(ns+"ibox1");
//		model.add(iblockRes1, RDF.type, BlockGeomObjectOnt.InnerBlock);
//		Resource oblockRes1 = model.createResource(ns+"obox1");
//		model.add(oblockRes1, RDF.type, BlockGeomObjectOnt.OuterBlock);
//		
//		Seq vpSeq = model.createSeq();
//		vpSeq.add(box);
//		model.add(vp, BlockGeomObjectOnt.structContainsBlocks, vpSeq);
//		
//		
//		Seq boxSeq = model.createSeq();
//		boxSeq.add(iblock);
//		boxSeq.add(oblock);
//		model.add(box, BlockGeomObjectOnt.structContainsBlocks, boxSeq);
//		
//		
//		System.out.println("time elapsed:"+stopwatch);
//		stopwatch.reset();stopwatch.start();
//		
//		wpModel = inj.getInstance(WPOntModel.class); // test
//		
//		System.out.println("time elapsed:"+stopwatch);
//		stopwatch.reset();stopwatch.start();
//		
//		RdfInstAdpFactoryWrap factory = inj.getInstance(RdfInstAdpFactoryWrap.class);
//		
//		System.out.println("time elapsed:"+stopwatch);
//		stopwatch.reset();stopwatch.start();
//		
//		StmtIterator iter = model.listStatements(vp, BlockGeomObjectOnt.structContainsBlocks
//				, (RDFNode)null);
//		vpSeq = iter.nextStatement().getSeq();
//		NodeIterator iter2 = vpSeq.iterator();
//		while (iter2.hasNext()) {
//			System.out.println(iter2.nextNode().asResource());
//		}
//		
//		System.out.println("time elapsed:"+stopwatch);
//		stopwatch.reset();stopwatch.start();
//		
//		IViewPortBlock vpb = factory.createAdp(vp, IViewPortBlock.class);
//		Set<IBlock> blockSet = vpb.getStructContainedBlocks();
//		System.out.println("viewport");
//		System.out.println(blockSet);
//		
//		System.out.println(vpb.canAs(IBoundingBlock.class));
//		IBoundingBlock bb = vpb.as(IBoundingBlock.class);
//		System.out.println("bounding block");
//		
//		System.out.println(vpb.canAs(IInnerBlock.class));
//		
//		System.out.println("time elapsed:"+stopwatch);
//		stopwatch.reset();stopwatch.start();
//		
//		IBox bbbb = blockSet.iterator().next().as(IBox.class);
//		System.out.println(bbbb);
//		IInnerBlock iib = bbbb.getInnerBlock();
//		Preconditions.checkNotNull(iib);
//		
//		System.out.println("time elapsed:"+stopwatch);
//		stopwatch.reset();stopwatch.start();
//		
//		System.out.println("box: "+bbbb.getOuterBlock().getBox());
//		
//		System.out.println(iib.getBox());
//		
//		stopwatch.stop();
//	    System.out.println("time elapsed:"+stopwatch);
//	}

}
