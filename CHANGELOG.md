# fcs-korp-endpoint
The Korp fcs 2.0 ILC4CLARIN endpoint implementation. (KORPEP)
Forked from https://github.com/clarin-eric/fcs-korp-endpoint

Distributed under http://www.gnu.org/licenses/gpl-3.0.txt GNU General Public License

Author riccardo.delgratta@ilc.cnr.it|gmail.com

# added features
## Property File
In the web.xml file add a parameter
<init-param>
      <param-name>eu.clarin.sru.server.propertyFile</param-name>
      <param-value><SOME_PATH>config-endpoint.properties</param-value>
</init-param>

The mandatory properties in the property file are the following:

### section on servers: tomcat and korp
Tomcat URL -> serverUrl=localhost
Tomcat ServerPort. -> serverPort=8080
useServerPort=True -> if True the url is url:port


Korp Url -> korpUrl=localhost
korpPort -> korpPort=90
useKorpPort=True -> if True the url is url:port

### section on context and pos translator
ILC4CLARIN_CORPORA=RICCARDO,PAROLE -> KORP Search Engine, however uses these ones to search on. It is a protection for potential test data in KORPSE. 
You can remove this property if all provided data are ok
DEFCONTEXT=hdl:20.500.11752/corpora -> See the ilc4clarin-korp-endpoint-description.xml file. This mandatory property tells KORPEP which is the default context
CTX2CORPORA=hdl:20.500.11752/parole=PAROLE,hdl:20.500.11752/riccardo=RICCARDO -> again this mandatory property must mach the ids in ilc4clarin-korp-endpoint-description.xml
CORPORA2POS=PAROLE=UD,RICCARDO=S-EAGLES -> Mandatory property for mapping corpora to their tagsets

## Quick start

Call 'mvn war:war' to create a war file. 

There are though some configurations to change if you want to use it with your own Korp service.

