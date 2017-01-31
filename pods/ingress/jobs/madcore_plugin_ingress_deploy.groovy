pipelineJob('madcore.plugin.ingress.deploy') {
  parameters {
    stringParam('IngressInstanceIP', '', 'IP address Ingress instance from cf output')
  }
    definition {
	    cps {
            sandbox()
            //script("""
            @NonCPS
            def shellCommand (String command) {
              return output = ['bash', '-c', command].execute().in.text
            }
            node {
                stage('Ingress: cloud formation install') {
                }
                stage ('Initialize ingress node') {
                  node_name = shellCommand ("ssh -i /home/ubuntu/id_rsa -i /home/ubuntu/.ssl/id_rsa -o StrictHostKeychecking=no ubuntu@${IngressInstanceIP} \"echo \$HOSTNAME\"")
                  sh "kubectl label node ${node_name} role=loadbalancer"
                }
                stage('Ingress: deploy pods') {
                    build job: 'madcore.kubectl.create', parameters: [string(name: 'FILENAME', value: 'pods/ingress/kub')]
                }
              }
            //""".stripIndent())
	    }
    }
}
