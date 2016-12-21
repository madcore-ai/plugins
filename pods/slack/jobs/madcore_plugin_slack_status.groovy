pipelineJob('madcore.plugin.slack.status') {
    parameters {
	    stringParam('APP_NAME', 'slack', '')
    }

    definition {
	    cps {
            sandbox()
            script("""
            node {
                build job: 'madcore.docker.registry.status', parameters: [string(name: 'APP_NAME', value: params.APP_NAME)]
                stage 'Kubernetes: status'
                build 'madcore.kubectl.describe', parameters: [string(name: 'FILENAME', 'slack/kub')]
                }
            """.stripIndent())
	    }
    }
}
