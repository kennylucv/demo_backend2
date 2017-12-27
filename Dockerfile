FROM websphere-liberty:webProfile7

RUN chmod 777 -R /config/

RUN chmod 777 -R /opt/

COPY server.xml /config/

COPY api.war /opt/ibm/wlp/usr/servers/defaultServer/dropins

ARG REPOSITORIES_PROPERTIES=""

RUN if [ ! -z $REPOSITORIES_PROPERTIES ]; then echo $REPOSITORIES_PROPERTIES > /opt/ibm/wlp/etc/repositories.properties; fi \
    && installUtility install --acceptLicense appSecurityClient-1.0 javaee-7.0 javaeeClient-7.0 \
    && if [ ! -z $REPOSITORIES_PROPERTIES ] ; then rm /opt/ibm/wlp/etc/repositories.properties; fi \
    && rm -rf /output/workarea /output/logs

EXPOSE 9080