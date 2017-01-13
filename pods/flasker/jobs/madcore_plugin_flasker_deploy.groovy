pipelineJob('madcore.plugin.flasker.deploy') {
    parameters {
      stringParam('REPO_URL', '', '')
      stringParam('REPO_BRANCH', 'master', '')
      stringParam('APP_NAME', 'flasker', '')
      stringParam('PORT', '', '')
      stringParam('DOCKERFILE_PATH', 'flasker', 'Specify path to docker file relative to root repo.')
    }

    definition {
	    cps {
            sandbox()
            script("""
            node {
                stage 'Docker: build container'
                build job: 'madcore.docker.image.build', parameters: [string(name: 'REPO_URL', value: params.REPO_URL), string(name: 'APP_NAME', value: params.APP_NAME), string(name: 'DOCKERFILE_PATH', value: params.DOCKERFILE_PATH)]
                stage 'Docker: registry publish'
                build job: 'madcore.docker.registry.publish', parameters: [string(name: 'APP_NAME', value: params.APP_NAME)]
                stage 'Docker: registry status'
                build job: 'madcore.docker.registry.status', parameters: [string(name: 'APP_NAME', value: params.APP_NAME)]
                stage 'Kubernetes: create'
                build job: 'madcore.kubectl.create', parameters: [string(name: 'FILENAME', value: 'flasker/kub')]
                stage 'Update app base'
                build job: 'madcore.redis.app.update', parameter: [string(name: 'APP_NAME', value: params.APP_NAME)]
                stage 'Update certificate and haproxy'
                build job: 'madcore.ssl.letsencrypt.getandinstall'
                }
            """.stripIndent())
	    }
    }
}
