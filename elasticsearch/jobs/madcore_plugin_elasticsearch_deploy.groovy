pipelineJob('madcore.plugin.elasticsearch.deploy') {
    parameters {
	    stringParam('APP_NAME', 'elasticsearch', '')
      stringParam('S3BUCKETNAME', '', 'S3 bucket name for backup')
    }

    definition {
	    cps {
            sandbox()
            script("""
            node {
                stage('Ingress: cloud formation install') {
                }
                stage('Elasticsearch: create services') {
                    build job: 'madcore.kubectl.create', parameters: [string(name: 'FILENAME', value: 'elasticsearch/kub/services')]
                }
                stage('Elasticsearch: create master') {
                    build job: 'madcore.kubectl.create', parameters: [string(name: 'FILENAME', value: 'elasticsearch/kub/es-master-rc.yaml')]
                }
                stage('Elasticsearch: wait for master') {
                    build job: 'madcore.kubectl.wait.pod.up', parameters: [string(name: 'POD_NAME', value: 'es-master')]
                }
                stage('Elasticsearch: create client') {
                    build job: 'madcore.kubectl.create', parameters: [string(name: 'FILENAME', value: 'elasticsearch/kub/es-client-rc.yaml')]
                }
                stage('Elasticsearch: wait for client') {
                    build job: 'madcore.kubectl.wait.pod.up', parameters: [string(name: 'POD_NAME', value: 'es-client')]
                }
                stage('Elasticsearch: create data') {
                    build job: 'madcore.kubectl.create', parameters: [string(name: 'FILENAME', value: 'elasticsearch/kub/es-data-rc.yaml')]
                }
                stage('Elasticsearch: wait for elasticsearch cluster to start') {
                    build job: 'madcore.kubectl.wait.service.up', parameters: [string(name: 'APP_NAME', value: params.APP_NAME), string(name: 'SERVICE_NAME', value: 'elasticsearch-main:9200'), string(name: 'SERVICE_NAMESPACE', value: 'es-cluster')]
                }
                stage('Update HAproxy') {
                    build job: 'madcore.ssl.letsencrypt.getandinstall', parameters: [string(name: 'S3BucketName', value: params.S3BUCKETNAME)]
                }
              }
            """.stripIndent())
	    }
    }
}
