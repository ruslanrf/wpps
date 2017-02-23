# WPPS
A Web Page Processing System

WPPS, Version 1.0.0.

This repository contains WPPS framework and ObjIdent tool (the `tuwien.dbai.wpps.objident` plugin) based on WPPS for computing features.
Both are Eclipse (Indigo) RCP based cross-platform applications implemented in the Java language (JDK 1.7.0).
WPPS has [XULRunner](http://archive.mozilla.org/pub/xulrunner/releases/) (version 1.9.2 corresponding to Firefox of the version 3.6) integrated for rendering web pages.
XULRunner is a platform introduced by Mozilla for building rich Internet applications and has Gecko layout engine in its core.
WPPS also utilizes the [ATF project](http://www.eclipse.org/atf/) plug-ins of the version 0.3.0 (published under the Eclipse Public License v1.0), which enables seamless integration of XULRunner within the Eclipse RCP platform and conveys additional graphical components and widgets for interacting with the web browser (i.e., XULRunner).

## Installation and Execution

### WPPS

The WPPS framework requires Eclipse (Indigo) RCP, and [Zest v. 2.0.0](https://github.com/ujhelyiz/zest) to be installed.
All other necessary plug-ins are provided separately as a target platform, `wpps_platform`.

The simplest approach to "install" WPPS is
**(1)** to [download](https://sourceforge.net/projects/wpps-platforms/files/1.0.0/wpps_platforms.zip/download) Eclipse with Zest plugins as well as the WPPS platform.
**(2)** Unpack a downloaded `wpps_platforms.zip` and execute Eclipse, stored in the folder `eclipseRcpIndigoSR2LinuxGtkX86_64WPPS`.
**(3)** Specify the [target platform](http://www.vogella.com/tutorials/EclipseTargetPlatform/article.html). For that open a file `wpps.target` in the plugin `tuwien.dbai.wpps.releng`.
In the Location section ensure that `${eclipse_home}` points at the folder `eclipseRcpIndigoSR2LinuxGtkX86_64WPPS`.
For the variable `${wpps_platform}`, specify a file path to the directory `wpps_platform` and click "Set as Target Platform" (top-right corner).
**(4)** [Import a launch configuration](http://www.vogella.com/tutorials/EclipseLauncherFramework/article.html#import-a-launch-configuration) `WPPSUI.launch` from the directory `launch_configs`.
This configuration can be used to execute the WPPS framework.

### ObjIdent

Please follow steps **(1--3)** for installing WPPS and import a launch configuration `ObjIdent.launch` from the directory `launch_configs`.
This configuration can be used to execute ObjIdent for annotating web pages.
The GUI is configured for annotating pages with labeles from the transportation domain.


The WPPS target platform has only XULRunner for Linux with x86-64 architecture and can be only executed on that platform.
Other versions of XULRunner can be acquired from [Mozilla's archive](http://archive.mozilla.org/pub/xulrunner/releases/).

## Citation

If you use WPPS, please cite one of the following publications in your articles (a BibTeX format):
```bibtex
@inproceedings{Fayzrakhmanov2012WISE,
  author = {Fayzrakhmanov, Ruslan R.},
  booktitle = {In Proceedings of the 13th International Conference on Web Information Systems Engineering (WISE'2012), Demo Session, Paphos, Cyprus, 28–30 November, 2012},
  editor = {Wang, X. Sean and Cruz, Isabel and Delis, Alex and Huang, Guangyan},
  pages = {800--803},
  publisher = {Springer},
  title = {{WPPS: A framework for web page processing}},
  year = {2012},
  doi = {10.1007/978-3-642-35063-4_70}
}
```
```bibtex
@incollection{Fayzrakhmanov2014IGIGlobal,
author = {Fayzrakhmanov, Ruslan R.},
booktitle = {The Evolution of the Internet in the Business Sector: Web 1.0 to Web 3.0},
chapter = {2},
doi = {10.4018/978-1-4666-7262-8},
editor = {Isa\'{\i}as, Pedro and Kommers, Piet and Issa, Tomayess},
isbn = {9781466672628},
pages = {25--50},
publisher = {IGI Global},
title = {{Models and Approaches for Web Information Extraction and Web Page Understanding}},
year = {2015}
}
```
```bibtex
@inproceedings{Fayzrakhmanov2012ICWI,
address = {Madrid},
author = {Fayzrakhmanov, Ruslan R.},
booktitle = {Proceeding of the International Conference IADIS WWW/Internet, Madrid, 18–21 October, 2012},
editor = {White, Bebo and Isa\'{\i}as, Pedro},
pages = {19--26},
publisher = {IADIS Press},
title = {{WPPS: A novel and comprehensive framework for web page understanding and information extraction}},
year = {2012}
}
```

If you use ObjIdent, please cite the following publication in your articles (a BibTeX format):
```
@inproceedings{Kordomatis2013WIMS,
author = {Kordomatis, Iraklis and Herzog, Christoph and Fayzrakhmanov, Ruslan R. and Kr\"{u}pl-Sypien, Bernhard and Holzinger, Wolfgang and Baumgartner, Robert},
title = {Web object identification for web automation and meta-search},
booktitle = {Proceedings of the 3rd International Conference on Web Intelligence, Mining and Semantics},
series = {WIMS '13},
year = {2013},
isbn = {978-1-4503-1850-1},
location = {Madrid, Spain},
pages = {13:1--13:12},
articleno = {13},
numpages = {12},
url = {http://doi.acm.org/10.1145/2479787.2479798},
doi = {10.1145/2479787.2479798},
acmid = {2479798},
publisher = {ACM},
address = {New York, NY, USA}
}
```

## Authors
 * [Ruslan Fayzrakhmanov](http://www.dbai.tuwien.ac.at/staff/fayzrakh/)

## License

This project is licensed under the GNU Library General Public License, Version 2,
June 1991 (in the distribution as file `LICENSE.txt`).
This license has been chosen due to the use of the code from Gate (a class `ColorGenerator.java` in the `tuwien.dbai.wpps.colors` plugin) published under this license and
Java Spatial Index Library (the `tuwien.dbai.wpps.core.spatialindex.jsi` package in the `tuwien.dbai.wpps.core` plugin) published under LGPL v. 2.1.
